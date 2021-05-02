package com.lukianbat.feature.weather.common.di

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.WeatherFlowFragment
import com.lukianbat.feature.weather.common.di.modules.DatabaseModule
import com.lukianbat.feature.weather.common.di.modules.NetworkModule
import com.lukianbat.feature.weather.common.di.modules.WeatherFlowModule
import com.lukianbat.feature.weather.features.description.di.WeatherDescriptionComponent
import com.lukianbat.feature.weather.features.weather.di.WeatherComponent
import dagger.Module
import dagger.Subcomponent

@FlowScope
@Subcomponent(
    modules = [
        WeatherFlowSubcomponentsModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        WeatherFlowModule::class
    ]
)
interface WeatherFlowComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherFlowComponent
    }

    fun weatherComponent(): WeatherComponent.Factory

    fun weatherDescriptionComponent(): WeatherDescriptionComponent.Factory

    fun inject(fragment: WeatherFlowFragment)
}

@Module(subcomponents = [WeatherComponent::class, WeatherDescriptionComponent::class])
object WeatherFlowSubcomponentsModule