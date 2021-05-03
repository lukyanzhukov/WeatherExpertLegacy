package com.lukianbat.feature.city.presentation

import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.core.common.model.CityModel
import com.lukianbat.feature.city.di.CityComponentController
import com.lukianbat.feature.city.domain.usecase.CityInteractor
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChooseCityViewModel @Inject constructor(
    private val interactor: CityInteractor,
    private val errorAdapter: RxViewOutput.ErrorAdapter,
    private val cityComponentController: CityComponentController
) : RxViewModel() {

    private val cities = RxViewOutput<CitiesSearchAction>(this, RxViewOutput.Strategy.ONCE)
    private val onNext = RxViewOutput<CityModel>(this, RxViewOutput.Strategy.ONCE)
    private val savedCity = RxViewOutput<Optional<CityModel>>(this)

    private val citiesBehaviorSubject = BehaviorSubject.create<String>()
    private var selectedCityModel: CityModel? = null

    init {
        val savedCityObservable = interactor.getLastCity()
            .doOnSuccess { selectedCityModel = it.toNullable() }
            .toObservable()

        val searchOrganizationsObservable = citiesBehaviorSubject
            .doOnNext { selectedCityModel = null }
            .debounce(REQUEST_DELAY, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { name ->
                if (name.length < CITY_NAME_MIN_LENGTH) {
                    return@switchMap Observable.just(CitiesSearchAction.WrongCitiesFormatInput)
                }
                interactor.searchCity(name)
                    .flatMap { cities ->
                        if (cities.isNullOrEmpty()) return@flatMap Single.just(CitiesSearchAction.CitiesNotFound)
                        Single.just(
                            CitiesSearchAction.CitiesFound(
                                cities.map { CityListItem.CityItem(it) }
                            )
                        )
                    }
                    .toObservable()
            }

        cities.source(searchOrganizationsObservable, errorAdapter)
        savedCity.source(savedCityObservable, errorAdapter)
    }

    fun cities() = cities.asOutput()

    fun onNext() = onNext.asOutput()

    fun savedCity() = savedCity.asOutput()

    fun onCitySelected(item: CityModel) {
        selectedCityModel = item
        savedCity.valueSource(item.toOptional())
    }

    fun onCityNameChanged(name: String) {
        if (name == selectedCityModel?.name) return
        savedCity.valueSource(null.toOptional())
        citiesBehaviorSubject.onNext(name)
        cities.retry()
    }

    fun onNextButtonClicked() {
        selectedCityModel?.let {
            onNext.source(
                interactor.saveCity(it)
                    .andThen(Observable.just(it)),
                errorAdapter
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        cityComponentController.clearCityComponent()
    }

    companion object {
        private const val REQUEST_DELAY = 300L
        private const val CITY_NAME_MIN_LENGTH = 3
    }
}
