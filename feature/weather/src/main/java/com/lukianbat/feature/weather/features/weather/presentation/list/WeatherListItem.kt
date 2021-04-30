package com.lukianbat.feature.weather.features.weather.presentation.list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class WeatherListItem {

    data class TitleItem(@StringRes val textResId: Int) : WeatherListItem()

    data class DescriptionItem(val note: String = "") : WeatherListItem()

    data class ButtonItem(@StringRes val textResId: Int) : WeatherListItem()

    data class WeatherCardItem(
        @DrawableRes val weatherImageResId: Int,
        val temp: Int,
        val feelsLikeTemp: Int,
        val minTemp: Int,
        val maxTemp: Int,
        val humidity: Int,
        val windSpeed: Double,
        val date: String
    ) : WeatherListItem()

    object Divider : WeatherListItem()

    object ErrorItem : WeatherListItem()
}
