package com.lukianbat.feature.weather.common.data.local.gateway

import com.lukianbat.core.common.model.CityModel
import com.lukianbat.feature.weather.common.data.local.mapper.DBMapper.toDomain
import com.lukianbat.weatherexpertdb.WeatherExpertDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherLocalGateway @Inject constructor(private val dao: WeatherExpertDao) {

    fun getCityByName(cityName: String): Single<CityModel> = dao
        .getCityByName(cityName)
        .map { it.toDomain() }
        .subscribeOn(Schedulers.io())

    fun getCities(): Single<List<CityModel>> = dao
        .getCities()
        .map { cities -> cities.map { it.toDomain() } }
        .subscribeOn(Schedulers.io())
}
