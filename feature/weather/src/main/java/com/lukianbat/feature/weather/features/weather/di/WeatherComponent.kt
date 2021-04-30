package com.lukianbat.feature.weather.features.weather.di

import com.lukianbat.feature.weather.features.weather.presentation.WeatherFragment
import dagger.Subcomponent

@Subcomponent(modules = [WeatherModule::class])
interface WeatherComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherComponent
    }

    fun inject(fragment: WeatherFragment)
}
