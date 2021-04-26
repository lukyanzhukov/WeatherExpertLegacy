package com.lukianbat.weatherexpertlegacy.di.module

import android.content.Context
import android.util.Log
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.architecture.network.NetworkLoggingInterceptor
import com.lukianbat.core.common.gateway.SettingsGateway
import com.lukianbat.prefser.Prefser
import com.lukianbat.weatherexpertdb.WeatherExpertDao
import com.lukianbat.weatherexpertdb.WeatherExpertDatabase
import com.lukianbat.weatherexpertlegacy.DefaultErrorAdapter
import com.lukianbat.weatherexpertlegacy.domain.LocalSettingsGateway
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(READ_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(
                NetworkLoggingInterceptor {
                    Log.i(NETWORK_LOG_TAG, it)
                }.apply { level = NetworkLoggingInterceptor.Level.BODY }
            )
            .build()

    }

    @Provides
    @Singleton
    fun provideErrorAdapter(context: Context): RxViewOutput.ErrorAdapter {
        return DefaultErrorAdapter(context)
    }

    @Provides
    @Singleton
    fun providePrefser(context: Context): Prefser = Prefser(context)

    @Provides
    @Singleton
    fun provideLocalSettings(prefser: Prefser): SettingsGateway {
        return LocalSettingsGateway(prefser)
    }

    @Provides
    @Singleton
    fun provideWeatherExpertDatabase(context: Context): WeatherExpertDatabase {
        return WeatherExpertDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideCityDao(database: WeatherExpertDatabase): WeatherExpertDao = database.dao()

    companion object {
        private const val NETWORK_LOG_TAG = "NETWORK_LOG"
        private const val CONNECT_TIMEOUT = 10000L
        private const val READ_WRITE_TIMEOUT = 30000L
    }
}
