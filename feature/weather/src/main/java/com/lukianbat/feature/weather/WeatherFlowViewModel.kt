package com.lukianbat.feature.weather

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.feature.weather.common.di.WeatherFlowComponentController
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import javax.inject.Inject

class WeatherFlowViewModel @Inject constructor(
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val componentController: WeatherFlowComponentController
) : RxViewModel() {

    fun setCityName(name: String) {
        chosenCityNameGateway.cityName = name
    }

    override fun onCleared() {
        super.onCleared()
        componentController.clearWeatherFlowComponent()
    }
}
