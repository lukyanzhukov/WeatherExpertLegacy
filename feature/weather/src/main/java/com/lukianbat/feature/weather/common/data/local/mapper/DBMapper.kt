package com.lukianbat.feature.weather.common.data.local.mapper

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary
import com.lukianbat.weatherexpertdb.entity.CityDbModel
import com.lukianbat.weatherexpertdb.entity.WeatherDbModel
import com.lukianbat.weatherexpertdb.entity.WeatherTypeDbModel

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

    fun WeatherDbModel.toSummary() =
        WeatherSummary(
            id = weatherId,
            description = userDescription,
            type = type.toDomain(),
            temp = temp,
            humidity = humidity,
            windSpeed = windSpeed,
        )

    private fun WeatherTypeDbModel.toDomain(): WeatherModel.WeatherType {
        return when (this) {
            WeatherTypeDbModel.THUNDERSTORM -> WeatherModel.WeatherType.THUNDERSTORM
            WeatherTypeDbModel.DRIZZLE -> WeatherModel.WeatherType.DRIZZLE
            WeatherTypeDbModel.RAIN -> WeatherModel.WeatherType.RAIN
            WeatherTypeDbModel.SNOW -> WeatherModel.WeatherType.SNOW
            WeatherTypeDbModel.ATMOSPHERE -> WeatherModel.WeatherType.ATMOSPHERE
            WeatherTypeDbModel.CLEAR -> WeatherModel.WeatherType.CLEAR
            WeatherTypeDbModel.CLOUDS -> WeatherModel.WeatherType.CLOUDS
            else -> WeatherModel.WeatherType.NONE
        }
    }
}
