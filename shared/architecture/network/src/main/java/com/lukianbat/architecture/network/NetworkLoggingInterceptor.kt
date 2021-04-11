package com.lukianbat.architecture.network

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class NetworkLoggingInterceptor constructor(
    private val logger: (message: String) -> Unit = DEFAULT
) : Interceptor {
    var level = Level.NONE

    override fun intercept(chain: Interceptor.Chain): Response {
        val level = level
        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }
        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val connection = chain.connection()
        var requestMessage = "-->  ${request.method()} ${request.url()} ${connection?.protocol() ?: ' '}\n"
        if (!logHeaders && hasRequestBody) {
            requestMessage += " (${requestBody!!.contentLength()}-byte body)\n"
        }
        if (logHeaders) {
            if (hasRequestBody) {
                if (requestBody!!.contentType() != null) {
                    requestMessage += "Content-Type: ${requestBody.contentType()}\n"
                }
                if (requestBody.contentLength() != -1L) {
                    requestMessage += "Content-Length: ${requestBody.contentLength()}\n"
                }
            }
            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    requestMessage += "${logHeader(headers, i)}\n"
                }
                i++
            }
            if (!logBody || !hasRequestBody) {
                requestMessage += "--> END ${request.method()}\n"
            } else if (bodyHasUnknownEncoding(request.headers())) {
                requestMessage += "--> END ${request.method()} (encoded body omitted)\n"
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)
                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (isPlaintext(buffer)) {
                    requestMessage += "${buffer.readString(charset)}\n"
                    requestMessage += "--> END ${request.method()} (${requestBody.contentLength()}-byte body)\n"
                } else {
                    requestMessage += "--> END ${request.method()} (binary ${requestBody.contentLength()}-byte body omitted)\n"
                }
            }
        }

        logger(requestMessage)

        var responseMessage = ""
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            responseMessage += "<-- HTTP FAILED: $e \n"
            logger(responseMessage)
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        responseMessage += "<-- ${response.code()} ${response.message()} ${response.request().url()} ($tookMs ms ${if (!logHeaders) ", $bodySize body" else ""})\n"
        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                responseMessage += "${logHeader(headers, i)}\n"
                i++
            }
            if (!logBody || !HttpHeaders.hasBody(response)) {
                responseMessage += "<-- END HTTP\n"
            } else if (bodyHasUnknownEncoding(response.headers())) {
                responseMessage += "<-- END HTTP (encoded body omitted)\n"
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source.buffer()
                var gzippedLength: Long? = null
                if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                    gzippedLength = buffer.size()
                    var gzippedResponseBody: GzipSource? = null
                    try {
                        gzippedResponseBody = GzipSource(buffer.clone())
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    } finally {
                        gzippedResponseBody?.close()
                    }
                }
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (!isPlaintext(buffer)) {
                    responseMessage += "<-- END HTTP (binary ${buffer.size()}-byte body omitted)\n"
                    logger(responseMessage)
                    return response
                }
                if (contentLength != 0L) {
                    responseMessage += "${buffer.clone().readString(charset)}\n"
                }
                responseMessage += if (gzippedLength != null) {
                    "<-- END HTTP (${buffer.size()}-byte, ${gzippedLength}-gzipped-byte body)"
                } else {
                    "<-- END HTTP (${buffer.size()}-byte body)"
                }
            }
        }
        logger(responseMessage)
        return response
    }

    private fun logHeader(headers: Headers, i: Int): String {
        val value = headers.value(i)
        return "${headers.name(i)}: $value"
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return (contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
            && !contentEncoding.equals("gzip", ignoreCase = true))
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }

    enum class Level {
        NONE, BASIC, HEADERS, BODY
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        private val DEFAULT: (message: String) -> Unit = { message -> Platform.get().log(Platform.INFO, message, null) }
    }
}
