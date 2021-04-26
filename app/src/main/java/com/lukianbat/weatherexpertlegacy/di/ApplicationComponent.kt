package com.lukianbat.weatherexpertlegacy.di

import android.content.Context
import com.lukianbat.core.di.ViewModelBuilderModule
import com.lukianbat.feature.city.di.CityComponent
import com.lukianbat.feature.onboarding.presentation.di.OnboardingComponent
import com.lukianbat.feature.weather.di.WeatherFlowComponent
import com.lukianbat.weatherexpertlegacy.di.module.ApplicationModule
import com.lukianbat.weatherexpertlegacy.di.module.ComponentsControllerModule
import com.lukianbat.weatherexpertlegacy.start.di.StartFragmentComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ComponentsControllerModule::class,
        ViewModelBuilderModule::class,
        SubcomponentsModule::class
    ]
)
interface ApplicationComponent {

    fun startFragmentComponent(): StartFragmentComponent.Factory

    fun onboardingComponent(): OnboardingComponent.Factory

    fun cityComponent(): CityComponent.Factory

    fun weatherComponent(): WeatherFlowComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}

@Module(subcomponents = [StartFragmentComponent::class, OnboardingComponent::class])
object SubcomponentsModule