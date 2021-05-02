package com.lukianbat.feature.weather.common.domain.model

import org.threeten.bp.Instant

data class WeatherModel(
    val type: WeatherType,
    val temp: Int,
    val feelsLikeTemp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val humidity: Int,
    val windSpeed: Double,
    val date: Instant = Instant.now()
) {
    enum class WeatherType {
        THUNDERSTORM,
        DRIZZLE,
        RAIN,
        SNOW,
        ATMOSPHERE,
        CLEAR,
        CLOUDS,
        NONE
    }
}
