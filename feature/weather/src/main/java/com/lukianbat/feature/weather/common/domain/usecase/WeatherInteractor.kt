package com.lukianbat.feature.weather.common.domain.usecase

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.WeatherSummaryGateway
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import io.reactivex.Single
import javax.inject.Inject

@FlowScope
class WeatherInteractor @Inject constructor(
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val weatherSummaryGateway: WeatherSummaryGateway,
    private val localGateway: WeatherLocalGateway,
    private val remoteGateway: WeatherRemoteGateway
) {

    fun getCurrentWeather(): Single<WeatherModel> {
        return localGateway.getCityWithWeatherList(chosenCityNameGateway.cityName)
            .flatMap { (city, weatherList) ->
                remoteGateway.getWeather(city.name, city.regionCode, city.countryCode)
                    .map { currentWeather ->
                        val summary = weatherList.find {
                            it.humidity == currentWeather.humidity && it.temp == currentWeather.temp &&
                                    it.type == currentWeather.type && it.windSpeed == currentWeather.windSpeed
                        }
                            ?: return@map currentWeather
                        weatherSummaryGateway.weather = summary
                        return@map currentWeather.copy(description = summary.description)
                    }
            }
    }

    fun getForecast(): Single<List<WeatherModel>> {
        return localGateway.getCityByName(chosenCityNameGateway.cityName)
            .flatMap { remoteGateway.getForecast(it.name, it.regionCode, it.countryCode) }
    }

    fun getChosenCity(): Single<CityModel> {
        return localGateway.getCityByName(chosenCityNameGateway.cityName)
    }

    fun getCities(): Single<List<CityModel>> {
        return localGateway.getCities()
    }

    fun chooseCity(cityName: String) {
        chosenCityNameGateway.cityName = cityName
    }
}