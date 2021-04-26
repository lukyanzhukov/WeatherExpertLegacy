package com.lukianbat.feature.weather.data.local.mapper

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.weatherexpertdb.entity.CityDbModel

internal object DBMapper {

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
}
