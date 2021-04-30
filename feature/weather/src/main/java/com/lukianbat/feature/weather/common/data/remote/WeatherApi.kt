package com.lukianbat.feature.weather.common.data.remote

import com.lukianbat.feature.weather.common.data.remote.model.ForecastResponse
import com.lukianbat.feature.weather.common.data.remote.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(@Query("q") cityData: String): Single<WeatherResponse>

    @GET("forecast")
    fun getForecast(
        @Query("q") cityData: String,
        @Query("cnt") count: Int
    ): Single<ForecastResponse>
}
