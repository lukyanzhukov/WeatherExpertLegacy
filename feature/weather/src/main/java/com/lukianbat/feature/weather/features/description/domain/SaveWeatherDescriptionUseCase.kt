package com.lukianbat.feature.weather.features.description.domain

import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.feature.weather.common.domain.ChosenCityNameGateway
import com.lukianbat.feature.weather.common.domain.WeatherSummaryGateway
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary
import io.reactivex.Single
import javax.inject.Inject

class SaveWeatherDescriptionUseCase @Inject constructor(
    private val chosenCityNameGateway: ChosenCityNameGateway,
    private val weatherSummaryGateway: WeatherSummaryGateway,
    private val weatherLocalGateway: WeatherLocalGateway
) {
    operator fun invoke(description: String): Single<WeatherSummary> {
        return weatherSummaryGateway.getWeather()
            .firstOrError()
            .flatMap { summary ->
                weatherLocalGateway.saveWeatherDescription(
                    summary.copy(description = description),
                    chosenCityNameGateway.cityName
                )
            }
    }
}
