package com.lukianbat.feature.weather.common.data.remote

import com.lukianbat.architecture.data.Repository
import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import io.reactivex.Single
import javax.inject.Inject

@FlowScope
class WeatherApiRepository @Inject constructor(
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val localGateway: WeatherLocalGateway,
    private val remoteGateway: WeatherRemoteGateway
) : Repository<Pair<WeatherModel, List<WeatherModel>>>() {
    override fun load(): Single<Pair<WeatherModel, List<WeatherModel>>> {
        return localGateway.getCityByName(chosenCityNameGateway.cityName)
            .flatMap { city ->
                Single.zip(
                    remoteGateway.getWeather(city.name, city.regionCode, city.countryCode),
                    remoteGateway.getForecast(city.name, city.regionCode, city.countryCode),
                    { currentWeather, forecastList -> currentWeather to forecastList }
                )
            }
    }
}