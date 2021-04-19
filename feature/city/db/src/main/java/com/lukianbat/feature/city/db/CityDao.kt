package com.lukianbat.feature.city.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukianbat.feature.city.db.entity.CityDbModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun getCities(): Single<List<CityDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putCity(item: CityDbModel): Completable
}
