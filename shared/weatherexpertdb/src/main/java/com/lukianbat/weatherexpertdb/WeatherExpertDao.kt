package com.lukianbat.weatherexpertdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukianbat.weatherexpertdb.entity.CityDbModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherExpertDao {

    @Query("SELECT * FROM cities")
    fun getCities(): Single<List<CityDbModel>>

    @Query("SELECT * FROM cities WHERE city_name = :cityName LIMIT 1")
    fun getCityByName(cityName: String): Single<CityDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putCity(item: CityDbModel): Completable
}
