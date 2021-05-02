package com.lukianbat.feature.weather.features.description.presentation

import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.domain.model.WeatherSummaryModel
import com.lukianbat.feature.weather.common.utils.toFormatString
import org.threeten.bp.Instant

object DescriptionUIMapper {
    fun map(summary: WeatherSummaryModel): DescriptionUIModel {
        return DescriptionUIModel(
            summary.storeWeather?.description ?: "",
            R.drawable.ic_weather_cloudy,
            summary.currentWeather.temp,
            summary.currentWeather.humidity,
            summary.currentWeather.windSpeed,
            Instant.now().toFormatString()
        )
    }
}
