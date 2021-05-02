package com.lukianbat.weatherexpertlegacy

import android.app.Application
import com.lukianbat.core.utils.MutableLazy
import com.lukianbat.feature.city.di.CityComponent
import com.lukianbat.feature.city.di.CityComponentController
import com.lukianbat.feature.onboarding.presentation.di.OnboardingComponent
import com.lukianbat.feature.onboarding.presentation.di.OnboardingComponentController
import com.lukianbat.feature.weather.common.di.WeatherFlowComponent
import com.lukianbat.feature.weather.common.di.WeatherFlowComponentController
import com.lukianbat.weatherexpertlegacy.di.ApplicationComponent
import com.lukianbat.weatherexpertlegacy.di.DaggerApplicationComponent
import com.lukianbat.weatherexpertlegacy.start.di.StartFragmentComponent
import com.lukianbat.weatherexpertlegacy.start.di.StartFragmentComponentController

class App : Application(), OnboardingComponentController, CityComponentController,
    WeatherFlowComponentController, StartFragmentComponentController {

    lateinit var appComponent: ApplicationComponent

    private val onboardingComponent = MutableLazy.resettableLazy {
        appComponent.onboardingComponent().create()
    }

    private val cityComponent = MutableLazy.resettableLazy {
        appComponent.cityComponent().create()
    }

    private val weatherFlowComponent = MutableLazy.resettableLazy {
        appComponent.weatherComponent().create()
    }

    private val startComponent = MutableLazy.resettableLazy {
        appComponent.startFragmentComponent().create()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun provideOnboardingComponent(): OnboardingComponent {
        return requireNotNull(onboardingComponent.value)
    }

    override fun clearOnboardingComponent() {
        onboardingComponent.reset()
    }

    override fun provideCityComponent(): CityComponent = requireNotNull(cityComponent.value)

    override fun clearCityComponent() {
        cityComponent.reset()
    }

    override fun provideWeatherFlowComponent(): WeatherFlowComponent {
        return requireNotNull(weatherFlowComponent.value)
    }

    override fun clearWeatherFlowComponent() {
        weatherFlowComponent.reset()
    }

    override fun provideStartFragmentComponent(): StartFragmentComponent {
        return appComponent.startFragmentComponent().create()
    }

    override fun clearStartFragmentComponent() {
        startComponent.reset()
    }
}
