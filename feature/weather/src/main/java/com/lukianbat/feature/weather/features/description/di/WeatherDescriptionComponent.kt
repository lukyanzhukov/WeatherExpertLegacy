package com.lukianbat.feature.weather.features.description.di

import com.lukianbat.feature.weather.features.description.presentation.WeatherDescriptionFragment
import dagger.Subcomponent

@Subcomponent(modules = [WeatherDescriptionModule::class])
interface WeatherDescriptionComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherDescriptionComponent
    }

    fun inject(fragment: WeatherDescriptionFragment)
}
