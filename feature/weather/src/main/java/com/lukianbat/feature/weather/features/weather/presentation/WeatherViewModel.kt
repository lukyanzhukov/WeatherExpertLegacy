package com.lukianbat.feature.weather.features.weather.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.feature.weather.common.domain.usecase.WeatherInteractor
import com.lukianbat.feature.weather.features.weather.presentation.list.WeatherListItem
import io.reactivex.Single
import org.threeten.bp.Instant
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val interactor: WeatherInteractor,
    private val errorAdapter: RxViewOutput.ErrorAdapter,
) : RxViewModel() {

    private val chosenCity = RxViewOutput<CityUIModel>(this)
    private val weatherItems = RxViewOutput<List<WeatherListItem>>(this)
    private val savedCities = RxViewOutput<List<CityUIModel>>(this, RxViewOutput.Strategy.ONCE)
    private val showLoadingError = RxViewOutput<Boolean>(this, RxViewOutput.Strategy.ONCE)

    init {
        load()
    }

    fun weather() = weatherItems.asOutput()

    fun chosenCity() = chosenCity.asOutput()

    fun savedCities() = savedCities.asOutput()

    fun showLoadingError() = showLoadingError.asOutput()

    fun onCityClicked() {
        val savedCitiesSource = interactor.getCities()
            .map { cities -> cities.map { WeatherUIMapper.map(it) } }
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
        val weatherItemsSource =
            Single.zip(
                interactor.getCurrentWeather(),
                interactor.getForecast(),
                { currentWeather, forecast ->
                    currentWeather to forecast
                }
            )
                .map { (currentWeather, forecast) ->
                    WeatherUIMapper.map(
                        WeatherSummaryUIModel(
                            "Погода, конечно, хуйня, но не ебанина",
                            currentWeather,
                            forecast
                        )
                    )
                }
                .toObservable()

        val citySource = interactor.getChosenCity()
            .map { WeatherUIMapper.map(it) }
            .toObservable()

        chosenCity.source(citySource, errorAdapter)
        weatherItems.source(weatherItemsSource, errorAdapter)
    }
}
