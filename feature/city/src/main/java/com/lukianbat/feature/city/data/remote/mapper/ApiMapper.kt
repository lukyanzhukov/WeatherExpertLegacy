package com.lukianbat.feature.city.data.remote.mapper

import com.lukianbat.feature.city.data.remote.model.CitiesResponse
import com.lukianbat.feature.city.domain.model.CityModel

internal object ApiMapper {
    fun CitiesResponse.toDomain() = cities.map {
        CityModel(
            city = it.city,
            name = it.name,
            country = it.country,
            countryCode = it.countryCode,
            region = it.region,
            regionCode = it.regionCode,
            latitude = it.latitude,
            longitude = it.longitude
        )
    }
}
