package com.lukianbat.feature.city.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lukianbat.feature.city.db.entity.CityDbModel

@Database(
    version = 1,
    entities = [
        CityDbModel::class,
    ]
)
abstract class CityDatabase : RoomDatabase() {

    abstract fun dao(): CityDao

    companion object {
        fun create(context: Context): CityDatabase {
            return Room.databaseBuilder(
                context,
                CityDatabase::class.java,
                "city_db.db"
            )
                .build()
        }
    }
}
