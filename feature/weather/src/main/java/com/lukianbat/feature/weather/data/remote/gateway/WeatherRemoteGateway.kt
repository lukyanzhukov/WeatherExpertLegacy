package com.lukianbat.feature.weather.data.remote.gateway

import com.lukianbat.feature.weather.data.remote.WeatherApi
import com.lukianbat.feature.weather.data.remote.mapper.ApiMapper.toDomain
import com.lukianbat.feature.weather.domain.model.WeatherModel
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
}
