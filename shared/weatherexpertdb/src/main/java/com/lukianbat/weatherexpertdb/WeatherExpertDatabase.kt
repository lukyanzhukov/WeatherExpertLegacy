package com.lukianbat.weatherexpertdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lukianbat.weatherexpertdb.converters.WeatherTypeConverter
import com.lukianbat.weatherexpertdb.entity.CityDbModel
import com.lukianbat.weatherexpertdb.entity.WeatherDbModel

@Database(
    version = 1,
    entities = [
        CityDbModel::class,
        WeatherDbModel::class
    ]
)
@TypeConverters(
    WeatherTypeConverter::class,
)
abstract class WeatherExpertDatabase : RoomDatabase() {

    abstract fun dao(): WeatherExpertDao

    companion object {
        fun create(context: Context): WeatherExpertDatabase {
            return Room.databaseBuilder(
                context,
                WeatherExpertDatabase::class.java,
                "weather_expert_db"
            )
                .build()
        }
    }
}
