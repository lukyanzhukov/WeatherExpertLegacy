package com.lukianbat.feature.city.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class CitiesInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder()

        addHeaders(newBuilder)

        val newRequest = newBuilder.build()

        return chain.proceed(newRequest)
    }

    private fun addHeaders(builder: Request.Builder) {
        builder
            .header(HEADER_NAME, API_KEY)
    }

    companion object {
        private const val HEADER_NAME = "x-rapidapi-key"
        private const val API_KEY = "6d346ae6ccmshe322b133fc271fdp1e42f8jsn9dcfcd4f7518"
    }
}
