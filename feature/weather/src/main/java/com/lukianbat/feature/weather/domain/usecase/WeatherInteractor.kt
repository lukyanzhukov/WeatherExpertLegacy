package com.lukianbat.feature.weather.domain.usecase

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.domain.model.WeatherModel
import io.reactivex.Single
import javax.inject.Inject

@FlowScope
class WeatherInteractor @Inject constructor(
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val localGateway: WeatherLocalGateway,
    private val remoteGateway: WeatherRemoteGateway
) {

    fun getCurrentWeather(): Single<WeatherModel> {
        return localGateway.getCityByName(chosenCityNameGateway.cityName)
            .flatMap { remoteGateway.getWeather(it.name, it.regionCode, it.countryCode) }
    }

}