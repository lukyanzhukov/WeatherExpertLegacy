package com.lukianbat.feature.city.presentation

sealed class CitiesSearchAction {
    data class CitiesFound(val cities: List<CityListItem>) : CitiesSearchAction()

    object CitiesNotFound : CitiesSearchAction()

    object WrongCitiesFormatInput : CitiesSearchAction()
}
