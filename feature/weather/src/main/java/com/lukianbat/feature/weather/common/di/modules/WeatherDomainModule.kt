package com.lukianbat.feature.weather.common.di.modules

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.WeatherSummaryGateway
import com.lukianbat.feature.weather.common.domain.usecase.WeatherInteractor
import dagger.Module
import dagger.Provides

@Module
class WeatherDomainModule {

    @Provides
    @FlowScope
    fun provideWeatherInteractor(
        chosenCityNameGateway: ChosenCityNameGateway,
        weatherSummaryGateway: WeatherSummaryGateway,
        localGateway: WeatherLocalGateway,
        remoteGateway: WeatherRemoteGateway
    ): WeatherInteractor {
        return WeatherInteractor(
            chosenCityNameGateway,
            weatherSummaryGateway,
            localGateway,
            remoteGateway
        )
    }
}
