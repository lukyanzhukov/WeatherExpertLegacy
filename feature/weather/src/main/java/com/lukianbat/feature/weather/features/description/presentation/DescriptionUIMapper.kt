package com.lukianbat.feature.weather.features.description.presentation

import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary
import com.lukianbat.feature.weather.common.utils.toFormatString
import org.threeten.bp.Instant

object DescriptionUIMapper {
    fun map(summary: WeatherSummary): DescriptionUIModel {
        return DescriptionUIModel(
            summary.description,
            R.drawable.ic_weather_cloudy,
            summary.temp,
            summary.humidity,
            summary.windSpeed,
            Instant.now().toFormatString()
        )
    }
}
