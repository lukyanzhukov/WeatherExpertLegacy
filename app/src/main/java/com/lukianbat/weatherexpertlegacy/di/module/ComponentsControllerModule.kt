package com.lukianbat.weatherexpertlegacy.di.module

import android.content.Context
import com.lukianbat.feature.city.di.CityComponentController
import com.lukianbat.feature.onboarding.presentation.di.OnboardingComponentController
import com.lukianbat.feature.weather.di.WeatherFlowComponentController
import com.lukianbat.weatherexpertlegacy.start.di.StartFragmentComponentController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ComponentsControllerModule {

    @Singleton
    @Provides
    fun provideStartFragmentComponentController(context: Context): StartFragmentComponentController {
        return (context as StartFragmentComponentController)
    }

    @Singleton
    @Provides
    fun provideCityComponentController(context: Context): CityComponentController {
        return (context as CityComponentController)
    }

    @Singleton
    @Provides
    fun provideOnboardingComponentController(context: Context): OnboardingComponentController {
        return (context as OnboardingComponentController)
    }

    @Singleton
    @Provides
    fun provideWeatherFlowComponentController(context: Context): WeatherFlowComponentController {
        return (context as WeatherFlowComponentController)
    }
}
