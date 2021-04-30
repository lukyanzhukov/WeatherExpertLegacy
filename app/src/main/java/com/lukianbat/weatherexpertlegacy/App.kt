package com.lukianbat.weatherexpertlegacy

import android.app.Application
import com.lukianbat.core.utils.LazyMutable
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

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    private var onboardingComponent: OnboardingComponent? by LazyMutable {
        appComponent.onboardingComponent().create()
    }

    private var cityComponent: CityComponent? by LazyMutable {
        appComponent.cityComponent().create()
    }

    private var weatherFlowComponent: WeatherFlowComponent? by LazyMutable {
        appComponent.weatherComponent().create()
    }

    private var startComponent: StartFragmentComponent? by LazyMutable {
        appComponent.startFragmentComponent().create()
    }

    override fun provideOnboardingComponent(): OnboardingComponent {
        return requireNotNull(onboardingComponent)
    }

    override fun clearOnboardingComponent() {
        onboardingComponent = null
    }

    override fun provideCityComponent(): CityComponent = requireNotNull(cityComponent)

    override fun clearCityComponent() {
        cityComponent = null
    }

    override fun provideWeatherFlowComponent(): WeatherFlowComponent {
        return requireNotNull(weatherFlowComponent)
    }

    override fun clearWeatherFlowComponent() {
        weatherFlowComponent = null
    }

    override fun provideStartFragmentComponent(): StartFragmentComponent {
        return requireNotNull(startComponent)
    }

    override fun clearStartFragmentComponent() {
        startComponent = null
    }
}
