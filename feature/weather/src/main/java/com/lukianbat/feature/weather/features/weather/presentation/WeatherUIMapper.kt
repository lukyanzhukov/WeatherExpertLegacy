package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.feature.weather.R
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import com.lukianbat.feature.weather.common.domain.model.WeatherSummaryModel
import com.lukianbat.feature.weather.common.utils.toFormatString
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem

object WeatherUIMapper {

    fun map(weatherModel: WeatherSummaryModel): WeatherUIModel {
        val city = mapCity(weatherModel.cityModel)
        val items = mutableListOf<WeatherListItem>()
            .asSequence()
            .plus(WeatherListItem.TitleItem(R.string.weather_today_title))
            .plus(weatherModel.currentWeather.toWeatherItem())
            .plus(WeatherListItem.DescriptionItem(weatherModel.storeWeather?.description ?: ""))
            .plus(WeatherListItem.ButtonItem(R.string.weather_edit_description_btn))
            .plus(WeatherListItem.Divider)
            .plus(WeatherListItem.TitleItem(R.string.weather_forecast_title))
            .plus(forecastToWeatherList(weatherModel.forecast))
            .toList()

        return WeatherUIModel(city, items)
    }

    fun mapCity(cityModel: CityModel) = CityUIModel(cityModel.name, cityModel.country)

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
}
