package com.lukianbat.feature.weather.common.data

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.data.remote.WeatherApiRepository
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.model.WeatherSummaryModel
import io.reactivex.Observable
import javax.inject.Inject

@FlowScope
class WeatherRepository @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository,
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val localGateway: WeatherLocalGateway,
) {

    fun getWeatherSummaryModel(): Observable<WeatherSummaryModel> {
        return Observable.combineLatest(
            weatherApiRepository.data(),
            localGateway.getCityWithWeatherList(chosenCityNameGateway.cityName).toObservable(),
            { (currentWeather, forecast), (city, weatherList) ->
                val storeWeather = weatherList.find {
                    it.humidity == currentWeather.humidity && it.temp == currentWeather.temp &&
                            it.type == currentWeather.type && it.windSpeed == currentWeather.windSpeed
                }

                WeatherSummaryModel(
                    city,
                    storeWeather,
                    currentWeather,
                    forecast
                )
            }
        )
    }

    fun reload() {
        weatherApiRepository.reload()
    }
}
