package com.lukianbat.feature.weather.common.domain

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary
import javax.inject.Inject

@FlowScope
class WeatherSummaryGateway @Inject constructor() {
    var weather: WeatherSummary? = null
}