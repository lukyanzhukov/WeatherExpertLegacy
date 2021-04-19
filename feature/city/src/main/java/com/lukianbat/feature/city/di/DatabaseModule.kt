package com.lukianbat.feature.city.di

import android.content.Context
import com.lukianbat.feature.city.data.local.gateway.CitiesLocalGateway
import com.lukianbat.feature.city.db.CityDao
import com.lukianbat.feature.city.db.CityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideCityDatabase(@ApplicationContext context: Context): CityDatabase {
        return CityDatabase.create(context)
    }

    @Provides
    fun provideCityDao(database: CityDatabase): CityDao = database.dao()

    @Provides
    fun provideLocalGateway(dao: CityDao): CitiesLocalGateway = CitiesLocalGateway(dao)
}
