package com.lukianbat.feature.city.data.local.gateway

import com.lukianbat.feature.city.data.local.mapper.DBMapper.toDb
import com.lukianbat.feature.city.data.local.mapper.DBMapper.toDomain
import com.lukianbat.weatherexpertdb.WeatherExpertDao
import com.lukianbat.core.common.model.CityModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CitiesLocalGateway @Inject constructor(private val dao: WeatherExpertDao) {

    fun getCitiesList(): Single<List<CityModel>> = dao
        .getCities()
        .map { it.toDomain() }
        .subscribeOn(Schedulers.io())

    fun addCity(cityModel: CityModel): Completable {
        return dao.putCity(cityModel.toDb())
            .subscribeOn(Schedulers.io())
    }
}
