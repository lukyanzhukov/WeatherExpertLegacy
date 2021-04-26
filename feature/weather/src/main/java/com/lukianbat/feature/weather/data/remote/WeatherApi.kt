package com.lukianbat.feature.weather.data.remote

import com.lukianbat.feature.weather.data.remote.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(@Query("q") cityData: String): Single<WeatherResponse>
}
