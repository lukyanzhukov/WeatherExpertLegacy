package com.lukianbat.weatherexpertdb

import androidx.room.*
import com.lukianbat.weatherexpertdb.entity.CityDbModel
import com.lukianbat.weatherexpertdb.entity.CityWithWeatherList
import com.lukianbat.weatherexpertdb.entity.WeatherDbModel
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

    @Transaction
    @Query("SELECT * FROM cities WHERE city_name = :cityName LIMIT 1")
    fun getCityWithWeatherList(cityName: String): Single<CityWithWeatherList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherDbModel): Single<Long>
}
