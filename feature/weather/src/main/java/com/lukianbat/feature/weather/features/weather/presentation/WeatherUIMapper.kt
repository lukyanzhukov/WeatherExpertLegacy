package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

object WeatherUIMapper {
    private const val DATE_PATTERN = "eee, d MMM"

    fun map(weatherModel: WeatherSummaryUIModel): List<WeatherListItem> {
        return mutableListOf<WeatherListItem>()
            .asSequence()
            .plus(WeatherListItem.TitleItem(R.string.weather_today_title))
            .plus(weatherModel.currentWeather.toWeatherItem())
            .plus(WeatherListItem.DescriptionItem(weatherModel.userDescription))
            .plus(WeatherListItem.ButtonItem(R.string.weather_edit_description_btn))
            .plus(WeatherListItem.Divider)
            .plus(WeatherListItem.TitleItem(R.string.weather_forecast_title))
            .plus(forecastToWeatherList(weatherModel.forecast))
            .toList()
    }

    fun map(cityModel: CityModel) = CityUIModel(cityModel.name, cityModel.country)

    private fun forecastToWeatherList(list: List<WeatherModel>): List<WeatherListItem> {
        return list.map { it.toWeatherItem() }
    }

    private fun WeatherModel.toWeatherItem(): WeatherListItem {
        return WeatherListItem.WeatherCardItem(
            R.drawable.ic_weather_cloudy,
            this.temp,
            this.feelsLikeTemp,
            this.minTemp,
            this.maxTemp,
            this.humidity,
            this.windSpeed,
            this.date.toFormatString()
        )
    }

    private fun Instant.toFormatString() =
        DateTimeFormatter.ofPattern(DATE_PATTERN)
            .withZone(ZoneId.from(ZoneOffset.UTC))
            .format(this)
}
