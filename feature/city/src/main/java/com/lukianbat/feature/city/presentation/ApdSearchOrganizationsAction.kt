package com.lukianbat.feature.city.presentation

import com.lukianbat.feature.city.domain.model.CityModel

sealed class CitiesSearchAction {
    data class CitiesFound(val cities: List<CityModel>) : CitiesSearchAction()

    object CitiesNotFound : CitiesSearchAction()

    object WrongCitiesFormatInput : CitiesSearchAction()
}
