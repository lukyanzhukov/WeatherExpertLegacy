package com.lukianbat.feature.weather.features.description.presentation

import androidx.annotation.DrawableRes

data class DescriptionUIModel(
    val description: String,
    @DrawableRes val iconRes: Int,
    val temp: Int,
    val humidity: Int,
    val windSpeed: Double,
    val date: String
)