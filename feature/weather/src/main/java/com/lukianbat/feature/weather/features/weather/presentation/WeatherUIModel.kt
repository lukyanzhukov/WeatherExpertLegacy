package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

data class WeatherUIModel(
    val cityUIModel: CityUIModel,
    val items: List<WeatherListItem>
)