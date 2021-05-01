package com.lukianbat.feature.weather.common.domain.model

data class WeatherSummary(
    val id: Long,
    val description: String,
    val type: WeatherModel.WeatherType,
    val temp: Int,
    val humidity: Int,
    val windSpeed: Double,
)