package com.lukianbat.feature.city.di.modules

import com.lukianbat.feature.city.data.local.gateway.CitiesLocalGateway
import com.lukianbat.feature.city.data.remote.gateway.CitiesRemoteGateway
import com.lukianbat.feature.city.domain.usecase.CityInteractor
import dagger.Module
import dagger.Provides

@Module
class CityDomainModule {

    @Provides
    fun providesCitiesUseCase(
        remoteGateway: CitiesRemoteGateway,
        localGateway: CitiesLocalGateway
    ): CityInteractor {
        return CityInteractor(localGateway, remoteGateway)
    }
}