package com.lukianbat.feature.weather.common.data.local.mapper

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.feature.weather.common.domain.model.WeatherModel
import com.lukianbat.feature.weather.common.domain.model.WeatherSummary
import com.lukianbat.weatherexpertdb.entity.CityDbModel
import com.lukianbat.weatherexpertdb.entity.WeatherDbModel
import com.lukianbat.weatherexpertdb.entity.WeatherTypeDbModel

internal object DBMapper {

    private const val UNDEFINED = -1L
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

    fun WeatherSummary.toDb(cityName: String): WeatherDbModel {
        if (id == UNDEFINED) {
            return WeatherDbModel(
                cityName = cityName,
                type = type.toDb(),
                userDescription = description,
                temp = temp,
                humidity = humidity,
                windSpeed = windSpeed
            )
        }
        return WeatherDbModel(
            id,
            cityName,
            type.toDb(),
            description,
            temp,
            humidity,
            windSpeed
        )
    }

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

    private fun WeatherModel.WeatherType.toDb(): WeatherTypeDbModel {
        return when (this) {
            WeatherModel.WeatherType.THUNDERSTORM -> WeatherTypeDbModel.THUNDERSTORM
            WeatherModel.WeatherType.DRIZZLE -> WeatherTypeDbModel.DRIZZLE
            WeatherModel.WeatherType.RAIN -> WeatherTypeDbModel.RAIN
            WeatherModel.WeatherType.SNOW -> WeatherTypeDbModel.SNOW
            WeatherModel.WeatherType.ATMOSPHERE -> WeatherTypeDbModel.ATMOSPHERE
            WeatherModel.WeatherType.CLEAR -> WeatherTypeDbModel.CLEAR
            WeatherModel.WeatherType.CLOUDS -> WeatherTypeDbModel.CLOUDS
            else -> WeatherTypeDbModel.NONE
        }
    }
}
