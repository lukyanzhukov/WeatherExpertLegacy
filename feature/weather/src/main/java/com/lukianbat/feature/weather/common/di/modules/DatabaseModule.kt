package com.lukianbat.feature.weather.common.di.modules

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.local.gateway.WeatherLocalGateway
import com.lukianbat.weatherexpertdb.WeatherExpertDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @FlowScope
    fun provideLocalGateway(dao: WeatherExpertDao): WeatherLocalGateway = WeatherLocalGateway(dao)
}
