package com.lukianbat.feature.weather.common.di.modules

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.WeatherRepository
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.data.remote.WeatherApiRepository
import com.lukianbat.feature.weather.common.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.usecase.WeatherInteractor
import dagger.Module
import dagger.Provides

@Module
class WeatherDomainModule {

    @Provides
    @FlowScope
    fun providesWeatherApiRepository(
        remoteGateway: WeatherRemoteGateway,
        chosenCityNameGateway: ChosenCityNameGateway,
        localGateway: WeatherLocalGateway
    ): WeatherApiRepository {
        return WeatherApiRepository(chosenCityNameGateway, localGateway, remoteGateway)
    }

    @Provides
    @FlowScope
    fun provideWeatherRepository(
        apiRepository: WeatherApiRepository,
        chosenCityNameGateway: ChosenCityNameGateway,
        localGateway: WeatherLocalGateway
    ): WeatherRepository {
        return WeatherRepository(apiRepository, chosenCityNameGateway, localGateway)
    }

    @Provides
    @FlowScope
    fun provideWeatherInteractor(
        repository: WeatherRepository,
        chosenCityNameGateway: ChosenCityNameGateway,
        localGateway: WeatherLocalGateway
    ): WeatherInteractor {
        return WeatherInteractor(
            repository,
            chosenCityNameGateway,
            localGateway
        )
    }
}
