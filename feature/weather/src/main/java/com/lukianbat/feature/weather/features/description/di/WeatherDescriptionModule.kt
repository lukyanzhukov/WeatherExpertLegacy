package com.lukianbat.feature.weather.features.description.di

import androidx.lifecycle.ViewModel
import com.lukianbat.core.di.ViewModelKey
import com.lukianbat.feature.weather.features.description.presentation.WeatherDescriptionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherDescriptionModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDescriptionViewModel::class)
    abstract fun bindViewModel(viewModel: WeatherDescriptionViewModel): ViewModel
}
