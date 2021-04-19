package com.lukianbat.feature.city.di

import com.lukianbat.feature.city.data.local.gateway.CitiesLocalGateway
import com.lukianbat.weatherexpertdb.WeatherExpertDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideLocalGateway(dao: WeatherExpertDao): CitiesLocalGateway = CitiesLocalGateway(dao)
}
