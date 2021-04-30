package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import org.threeten.bp.Instant

data class WeatherSummaryUIModel(
    val userDescription: String,
    val currentWeather: WeatherModel,
    val forecast: List<WeatherModel>
) {
    data class WeatherUIModel(
        val weatherType: WeatherType,
        val temp: Int,
        val feelsLikeTemp: Int,
        val minTemp: Int,
        val maxTemp: Int,
        val humidity: Int,
        val windSpeed: Double,
        val date: Instant
    )

    enum class WeatherType {
        CLOUDY
    }
}
