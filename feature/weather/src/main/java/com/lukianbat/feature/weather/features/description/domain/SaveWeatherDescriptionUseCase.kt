package com.lukianbat.feature.weather.features.description.domain

import com.lukianbat.feature.weather.common.data.WeatherRepository
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.domain.model.StoreWeatherModel
import com.lukianbat.feature.weather.common.domain.model.WeatherSummaryModel
import io.reactivex.Single
import javax.inject.Inject

class SaveWeatherDescriptionUseCase @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherLocalGateway: WeatherLocalGateway
) {
    operator fun invoke(description: String): Single<StoreWeatherModel> {
        return repository.getWeatherSummaryModel()
            .firstOrError()
            .flatMap { summary ->
                weatherLocalGateway.saveWeatherDescription(
                    summary.getStoreWeather(description),
                    summary.cityModel.name
                )
            }
    }

    private fun WeatherSummaryModel.getStoreWeather(description: String): StoreWeatherModel {
        return storeWeather?.copy(description = description)
            ?: StoreWeatherModel(
                UNDEFINED,
                description,
                currentWeather.type,
                currentWeather.temp,
                currentWeather.humidity,
                currentWeather.windSpeed
            )
    }

    companion object {
        private const val UNDEFINED = -1L
    }
}
