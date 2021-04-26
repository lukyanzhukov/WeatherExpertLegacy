package com.lukianbat.feature.weather.weather.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.feature.weather.domain.model.WeatherModel
import com.lukianbat.feature.weather.domain.usecase.WeatherInteractor
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val interactor: WeatherInteractor,
    private val errorAdapter: RxViewOutput.ErrorAdapter
) : RxViewModel() {

    private val weather = RxViewOutput<WeatherModel>(this)

    init {
        val weatherSource = interactor.getCurrentWeather()
            .toObservable()

        weather.source(weatherSource, errorAdapter)
    }

    fun weather() = weather.asOutput()
}
