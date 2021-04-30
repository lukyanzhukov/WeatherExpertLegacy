package com.lukianbat.feature.weather.common.data.remote.gateway

import com.lukianbat.feature.weather.common.data.remote.WeatherApi
import com.lukianbat.feature.weather.common.data.remote.mapper.ApiMapper.toDomain
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import io.reactivex.Single
import javax.inject.Inject

class WeatherRemoteGateway @Inject constructor(
    private val weatherApi: WeatherApi
) {

    fun getWeather(
        cityName: String,
        regionCode: String,
        countryCode: String
    ): Single<WeatherModel> {
        return weatherApi.getWeather("${cityName},${regionCode},${countryCode}")
            .map { it.toDomain() }
    }

    fun getForecast(
        cityName: String,
        regionCode: String,
        countryCode: String
    ): Single<List<WeatherModel>> {
        return weatherApi.getForecast("${cityName},${regionCode},${countryCode}", 40)
            .map { it.toDomain() }
    }
}
