package com.lukianbat.feature.weather.features.description.presentation

import com.lukianbat.architecture.mvvm.RxViewModel
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.feature.weather.common.domain.usecase.WeatherInteractor
import com.lukianbat.feature.weather.features.description.domain.SaveWeatherDescriptionUseCase
import io.reactivex.Observable
import javax.inject.Inject

class WeatherDescriptionViewModel @Inject constructor(
    interactor: WeatherInteractor,
    private val errorAdapter: RxViewOutput.ErrorAdapter,
    private val saveDescriptionUseCase: SaveWeatherDescriptionUseCase
) : RxViewModel() {

    private val description = RxViewOutput<DescriptionUIModel>(this)
    private val onDescriptionSaved = RxViewOutput<Unit>(this, RxViewOutput.Strategy.ONCE)
    private lateinit var uiModel: DescriptionUIModel

    init {
        val descriptionSource = interactor.getSummary()
            .map {
                uiModel = DescriptionUIMapper.map(it)
                uiModel
            }

        description.source(descriptionSource, errorAdapter)
    }

    fun description() = description.asOutput()

    fun onDescriptionSaved() = onDescriptionSaved.asOutput()

    fun onSaveButtonClicked() {
        val onDescriptionSavedSource = saveDescriptionUseCase(uiModel.description)
            .flatMapObservable { Observable.just(Unit) }

        onDescriptionSaved.source(onDescriptionSavedSource, errorAdapter)
    }

    fun onDescriptionTextChanged(description: String) {
        uiModel = uiModel.copy(description = description)
    }
}
