package com.lukianbat.feature.weather.features.weather.di

import androidx.lifecycle.ViewModel
import com.lukianbat.core.di.ViewModelKey
import com.lukianbat.feature.weather.features.weather.presentation.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindViewModel(viewModel: WeatherViewModel): ViewModel
}
