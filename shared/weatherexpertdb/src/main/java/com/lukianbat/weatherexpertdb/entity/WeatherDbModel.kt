package com.lukianbat.weatherexpertdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "weathers_list",
    foreignKeys = [
        ForeignKey(
            entity = CityDbModel::class,
            parentColumns = ["city_name"],
            childColumns = ["weather_city_name"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class WeatherDbModel(
    @ColumnInfo(name = "weather_id") @PrimaryKey(autoGenerate = true) var weatherId: Long = 0,
    @ColumnInfo(name = "weather_city_name") var cityName: String,
    @ColumnInfo(name = "weather_type") val type: WeatherTypeDbModel,
    @ColumnInfo(name = "weather_description") val userDescription: String,
    @ColumnInfo(name = "weather_temp") val temp: Int,
    @ColumnInfo(name = "weather_humidity") val humidity: Int,
    @ColumnInfo(name = "weather_wind_speed") val windSpeed: Double
)
