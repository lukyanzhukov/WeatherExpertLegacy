package com.lukianbat.feature.weather.common.domain.model

import com.lukianbat.core.common.model.CityModel

data class WeatherSummaryModel(
    val cityModel: CityModel,
    val storeWeather: StoreWeatherModel?,
    val currentWeather: WeatherModel,
    val forecast: List<WeatherModel>,
)
