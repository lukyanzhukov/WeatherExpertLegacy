package com.lukianbat.feature.weather.common.domain

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@FlowScope
class WeatherSummaryGateway @Inject constructor() {
    private val weather: BehaviorSubject<WeatherSummary> = BehaviorSubject.create()

    fun setWeather(summary: WeatherSummary) {
        weather.onNext(summary)
    }

    fun getWeather(): BehaviorSubject<WeatherSummary> {
        return weather
    }
}