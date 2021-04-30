package com.lukianbat.feature.weather.common.data.remote.mapper

import com.lukianbat.feature.weather.common.data.remote.model.ForecastResponse
import com.lukianbat.feature.weather.common.data.remote.model.WeatherResponse
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

internal object ApiMapper {
    private const val KELVIN_CELSIUS_DIFF = 273.15

    fun WeatherResponse.toDomain(): WeatherModel {
        return WeatherModel(
            description = weatherDescription.firstOrNull()?.description ?: "",
            temp = main.temp.toCelsius(),
            feelsLikeTemp = main.feelsLike.toCelsius(),
            minTemp = main.tempMin.toCelsius(),
            maxTemp = main.tempMax.toCelsius(),
            humidity = main.humidity,
            windSpeed = wind.speed,
        )
    }

    fun ForecastResponse.toDomain(): List<WeatherModel> {
        var previousDay = Instant.ofEpochSecond(this.weatherItems.first().date)
            .atZone(ZoneId.from(ZoneOffset.UTC))
            .dayOfMonth

        return this.weatherItems
            .filter { listElement ->
                val dayOfMonth = Instant.ofEpochSecond(listElement.date)
                    .atZone(ZoneId.from(ZoneOffset.UTC))
                    .dayOfMonth
                val filter = previousDay != dayOfMonth

                previousDay = dayOfMonth
                return@filter filter
            }
            .map {
                WeatherModel(
                    description = it.weather.firstOrNull()?.description ?: "",
                    temp = it.main.temp.toCelsius(),
                    feelsLikeTemp = it.main.feelsLike.toCelsius(),
                    minTemp = it.main.minTemp.toCelsius(),
                    maxTemp = it.main.maxTemp.toCelsius(),
                    humidity = it.main.humidity,
                    windSpeed = it.wind.windSpeed,
                    date = Instant.ofEpochSecond(it.date) ?: Instant.now()
                )
            }
    }

    fun Double.toCelsius() = (this - KELVIN_CELSIUS_DIFF).toInt()
}
