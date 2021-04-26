package com.lukianbat.feature.weather.di

interface WeatherFlowComponentController {
    fun provideWeatherFlowComponent(): WeatherFlowComponent

    fun clearWeatherFlowComponent()
}
