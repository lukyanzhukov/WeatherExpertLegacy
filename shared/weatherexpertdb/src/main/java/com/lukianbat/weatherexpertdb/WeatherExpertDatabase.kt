package com.lukianbat.weatherexpertdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lukianbat.weatherexpertdb.entity.CityDbModel

@Database(
    version = 1,
    entities = [
        CityDbModel::class,
    ]
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
