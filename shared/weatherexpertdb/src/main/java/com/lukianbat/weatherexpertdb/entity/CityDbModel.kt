package com.lukianbat.weatherexpertdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityDbModel(
    @ColumnInfo(name = "city_current") val current: String,
    @ColumnInfo(name = "city_name") @PrimaryKey val name: String,
    @ColumnInfo(name = "city_country") val country: String,
    @ColumnInfo(name = "city_country_code") val countryCode: String,
    @ColumnInfo(name = "city_region") val region: String,
    @ColumnInfo(name = "city_region_code") val regionCode: String,
    @ColumnInfo(name = "city_latitude") val latitude: Double,
    @ColumnInfo(name = "city_longitude") val longitude: Double,
)
