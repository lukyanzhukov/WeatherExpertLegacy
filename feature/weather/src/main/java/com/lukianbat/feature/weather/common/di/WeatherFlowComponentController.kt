package com.lukianbat.feature.weather.common.di

interface WeatherFlowComponentController {
    fun provideWeatherFlowComponent(): WeatherFlowComponent

    fun clearWeatherFlowComponent()
}
