package com.lukianbat.feature.city.presentation

import com.lukianbat.core.common.model.CityModel

sealed class CitiesSearchAction {
    data class CitiesFound(val cities: List<CityModel>) : CitiesSearchAction()

    object CitiesNotFound : CitiesSearchAction()

    object WrongCitiesFormatInput : CitiesSearchAction()
}
