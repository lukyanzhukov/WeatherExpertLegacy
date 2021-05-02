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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun putCity(item: CityDbModel): Completable

    @Transaction
    @Query("SELECT * FROM cities WHERE city_name = :cityName LIMIT 1")
    fun getCityWithWeatherList(cityName: String): Single<CityWithWeatherList>

    @Query("SELECT * FROM weathers_list WHERE weather_id = :weatherId LIMIT 1")
    fun getWeatherById(weatherId: Long): Single<WeatherDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putWeather(weather: WeatherDbModel): Single<Long>
}
