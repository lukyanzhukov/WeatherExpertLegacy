package com.lukianbat.weatherexpertdb.converters

import androidx.room.TypeConverter
import com.lukianbat.weatherexpertdb.entity.WeatherTypeDbModel

class WeatherTypeConverter {

    @TypeConverter
    fun convertToDatabaseValue(entityProperty: WeatherTypeDbModel?): String? {
        return when (entityProperty) {
            WeatherTypeDbModel.THUNDERSTORM -> THUNDERSTORM
            WeatherTypeDbModel.DRIZZLE -> DRIZZLE
            WeatherTypeDbModel.RAIN -> RAIN
            WeatherTypeDbModel.SNOW -> SNOW
            WeatherTypeDbModel.ATMOSPHERE -> ATMOSPHERE
            WeatherTypeDbModel.CLEAR -> CLEAR
            WeatherTypeDbModel.CLOUDS -> CLOUDS
            else -> NONE
        }
    }

    @TypeConverter
    fun convertToEntityProperty(databaseValue: String): WeatherTypeDbModel {
        return when (databaseValue) {
            THUNDERSTORM -> WeatherTypeDbModel.THUNDERSTORM
            DRIZZLE -> WeatherTypeDbModel.DRIZZLE
            RAIN -> WeatherTypeDbModel.RAIN
            SNOW -> WeatherTypeDbModel.SNOW
            ATMOSPHERE -> WeatherTypeDbModel.ATMOSPHERE
            CLEAR -> WeatherTypeDbModel.CLEAR
            CLOUDS -> WeatherTypeDbModel.CLOUDS
            else -> WeatherTypeDbModel.NONE
        }
    }

    companion object {

        private const val THUNDERSTORM = "THUNDERSTORM"
        private const val DRIZZLE = "DRIZZLE"
        private const val RAIN = "RAIN"
        private const val SNOW = "SNOW"
        private const val ATMOSPHERE = "ATMOSPHERE"
        private const val CLEAR = "CLEAR"
        private const val CLOUDS = "CLOUDS"
        private const val NONE = "NONE"
    }
}
