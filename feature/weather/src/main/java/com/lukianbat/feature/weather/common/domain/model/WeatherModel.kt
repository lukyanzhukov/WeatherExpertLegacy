package com.lukianbat.feature.weather.common.domain.model

import org.threeten.bp.Instant

data class WeatherModel(
    val description: String,
    val temp: Int,
    val feelsLikeTemp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val humidity: Int,
    val windSpeed: Double,
    val date: Instant = Instant.now()
)
