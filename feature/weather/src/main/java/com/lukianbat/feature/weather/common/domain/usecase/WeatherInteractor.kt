package com.lukianbat.feature.weather.common.domain.usecase

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.WeatherRepository
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.data.remote.gateway.WeatherRemoteGateway
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import com.lukianbat.feature.weather.common.domain.model.WeatherSummaryModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@FlowScope
class WeatherInteractor @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val localGateway: WeatherLocalGateway,
) {

    fun getSummary(): Observable<WeatherSummaryModel> {
        return weatherRepository.getWeatherSummaryModel()
    }

    fun getCities(): Single<List<CityModel>> {
        return localGateway.getCities()
    }

    fun chooseCity(cityName: String) {
        chosenCityNameGateway.cityName = cityName
        weatherRepository.reload()
    }
}