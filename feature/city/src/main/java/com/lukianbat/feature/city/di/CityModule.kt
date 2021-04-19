package com.lukianbat.feature.city.di

import com.lukianbat.feature.city.data.local.gateway.CitiesLocalGateway
import com.lukianbat.feature.city.data.remote.gateway.CitiesRemoteGateway
import com.lukianbat.feature.city.domain.usecase.CityInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class CityModule {

    @Provides
    fun providesCitiesUseCase(
        remoteGateway: CitiesRemoteGateway,
        localGateway: CitiesLocalGateway
    ): CityInteractor {
        return CityInteractor(localGateway, remoteGateway)
    }
}
