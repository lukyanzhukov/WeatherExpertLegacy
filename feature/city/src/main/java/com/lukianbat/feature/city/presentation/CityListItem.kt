package com.lukianbat.feature.city.presentation

import androidx.annotation.StringRes
import com.lukianbat.core.common.model.CityModel

sealed class CityListItem {
    data class CityItem(val city: CityModel) : CityListItem()

    object CitySelectedItem : CityListItem()

    data class ErrorItem(@StringRes val textRes: Int) : CityListItem()
}
