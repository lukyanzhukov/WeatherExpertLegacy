package com.lukianbat.feature.city.data.local.mapper

import com.lukianbat.weatherexpertdb.entity.CityDbModel
import com.lukianbat.core.common.model.CityModel

internal object DBMapper {

    fun List<CityDbModel>.toDomain() = this.map { it.toDomain() }

    fun CityDbModel.toDomain() =
        CityModel(
            city = current,
            name = name,
            country = country,
            countryCode = countryCode,
            region = region,
            regionCode = regionCode,
            latitude = latitude,
            longitude = longitude
        )

    fun CityModel.toDb() = CityDbModel(
        current = city,
        name = name,
        country = country,
        countryCode = countryCode,
        region = region,
        regionCode = regionCode,
        latitude = latitude,
        longitude = longitude
    )
}
