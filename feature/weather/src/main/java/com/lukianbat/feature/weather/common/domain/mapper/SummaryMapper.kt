package com.lukianbat.feature.weather.common.domain.mapper

import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary

object SummaryMapper {
    const val UNDEFINED = -1L

    fun WeatherModel.toSummary(): WeatherSummary {
        return WeatherSummary(
            UNDEFINED,
            "",
            type,
            temp,
            humidity,
            windSpeed
        )
    }
}