package com.lukianbat.feature.weather.common.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class WeatherInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        val modifiedUrl = url.newBuilder()
            .addQueryParameter(PARAM_NAME, API_KEY)
            .build()

        val newBuilder = request.newBuilder()
            .url(modifiedUrl)

        val newRequest = newBuilder.build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val PARAM_NAME = "appid"
        private const val API_KEY = "a583a78067ccf7381380c6b9276179f7"
    }
}
