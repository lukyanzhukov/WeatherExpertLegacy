package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import org.threeten.bp.Instant

data class WeatherSummaryUIModel(
    val currentWeather: WeatherModel,
    val forecast: List<WeatherModel>
)