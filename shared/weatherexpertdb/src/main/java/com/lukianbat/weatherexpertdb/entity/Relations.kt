package com.lukianbat.weatherexpertdb.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithWeatherList(
    @Embedded val city: CityDbModel,
    @Relation(
        parentColumn = "city_name",
        entity = WeatherDbModel::class,
        entityColumn = "weather_city_name"
    )
    val weathersList: List<WeatherDbModel>
)