package com.lukianbat.feature.weather.data.remote.mapper

import com.lukianbat.feature.weather.data.remote.model.WeatherResponse
import com.lukianbat.feature.weather.domain.model.WeatherModel

internal object ApiMapper {
    private const val KELVIN_CELSIUS_DIFF = 273.15

    fun WeatherResponse.toDomain(): WeatherModel {
        return WeatherModel(
            description = weatherDescription.firstOrNull()?.description ?: "",
            icon = weatherDescription.firstOrNull()?.icon,
            temp = main.temp.toCelsius(),
            feelsLikeTemp = main.feelsLike.toCelsius(),
            minTemp = main.tempMin.toCelsius(),
            maxTemp = main.tempMax.toCelsius(),
            humidity = main.humidity,
            windSpeed = wind.speed,
        )
    }

    fun Double.toCelsius() = this - KELVIN_CELSIUS_DIFF
}
