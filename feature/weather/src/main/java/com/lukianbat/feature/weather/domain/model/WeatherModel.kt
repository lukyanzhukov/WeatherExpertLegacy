package com.lukianbat.feature.weather.domain.model

data class WeatherModel(
    val description: String,
    val icon: String?,
    val temp: Double,
    val feelsLikeTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val windSpeed: Double,
)
