package com.lukianbat.feature.weather.di.modules

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.domain.usecase.WeatherInteractor
import dagger.Module
import dagger.Provides

@Module
class WeatherDomainModule {

    @Provides
    @FlowScope
    fun provideWeatherInteractor(
        chosenCityNameGateway: ChosenCityNameGateway,
        localGateway: WeatherLocalGateway,
        remoteGateway: WeatherRemoteGateway
    ): WeatherInteractor {
        return WeatherInteractor(chosenCityNameGateway, localGateway, remoteGateway)
    }
}
