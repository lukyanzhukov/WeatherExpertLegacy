package com.lukianbat.feature.city.domain.usecase

import com.gojuno.koptional.toOptional
import com.lukianbat.feature.city.data.local.gateway.CitiesLocalGateway
import com.lukianbat.feature.city.data.remote.gateway.CitiesRemoteGateway
import com.lukianbat.core.common.model.CityModel
import javax.inject.Inject

class CityInteractor @Inject constructor(
    private val localGateway: CitiesLocalGateway,
    private val remoteGateway: CitiesRemoteGateway
) {

    fun searchCity(namePrefix: String) = remoteGateway.getCitiesList(namePrefix)

    fun getLastCity() = localGateway.getCitiesList()
        .map { it.last().toOptional() }

    fun saveCity(cityModel: CityModel) = localGateway.addCity(cityModel)

}
