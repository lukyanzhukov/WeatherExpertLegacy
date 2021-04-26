package com.lukianbat.feature.city.di.modules

import com.lukianbat.feature.city.data.local.gateway.CitiesLocalGateway
import com.lukianbat.weatherexpertdb.WeatherExpertDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideLocalGateway(dao: WeatherExpertDao): CitiesLocalGateway = CitiesLocalGateway(dao)
}
