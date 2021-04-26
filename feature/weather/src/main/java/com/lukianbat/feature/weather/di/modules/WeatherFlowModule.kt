package com.lukianbat.feature.weather.di.modules

import androidx.lifecycle.ViewModel
import com.lukianbat.core.di.FlowScope
import com.lukianbat.core.di.ViewModelKey
import com.lukianbat.feature.weather.WeatherFlowViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherFlowModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherFlowViewModel::class)
    @FlowScope
    abstract fun bindViewModel(viewModel: WeatherFlowViewModel): ViewModel
}
