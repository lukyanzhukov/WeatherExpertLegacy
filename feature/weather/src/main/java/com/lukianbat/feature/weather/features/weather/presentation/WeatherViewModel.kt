package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.feature.weather.common.domain.usecase.WeatherInteractor
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val interactor: WeatherInteractor,
    private val errorAdapter: RxViewOutput.ErrorAdapter,
) : RxViewModel() {

    private val weatherModel = RxViewOutput<WeatherUIModel>(this)
    private val savedCities = RxViewOutput<List<CityUIModel>>(this, RxViewOutput.Strategy.ONCE)
    private val showLoadingError = RxViewOutput<Boolean>(this, RxViewOutput.Strategy.ONCE)

    init {
        load()
    }

    fun weatherModel() = weatherModel.asOutput()

    fun savedCities() = savedCities.asOutput()

    fun showLoadingError() = showLoadingError.asOutput()

    fun onCityClicked() {
        val savedCitiesSource = interactor.getCities()
            .map { cities -> cities.map { WeatherUIMapper.mapCity(it) } }
            .toObservable()

        savedCities.source(savedCitiesSource, errorAdapter)
    }

    fun onCitySelected(cityUIModel: CityUIModel) {
        interactor.chooseCity(cityUIModel.cityName)
        showLoadingError.valueSource(false)
        load()
    }

    fun onRefreshButtonClicked() {
        showLoadingError.valueSource(false)
        load()
    }

    private fun load() {
        val weatherItemsSource = interactor.getSummary()
            .map(WeatherUIMapper::map)

        weatherModel.source(weatherItemsSource, errorAdapter)
    }
}
