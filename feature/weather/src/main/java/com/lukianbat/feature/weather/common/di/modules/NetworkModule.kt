package com.lukianbat.feature.weather.common.di.modules

import com.lukianbat.core.di.FlowScope
import com.lukianbat.feature.weather.common.data.remote.WeatherApi
import com.lukianbat.feature.weather.common.data.remote.WeatherInterceptor
import com.lukianbat.feature.weather.common.data.remote.gateway.WeatherRemoteGateway
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @Named(WEATHER_CLIENT)
    @FlowScope
    fun provideCitiesOkHttpClient(
        okHttpClient: OkHttpClient,
        authInterceptor: WeatherInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Named(WEATHER_CLIENT)
    @FlowScope
    fun provideCitiesRetrofit(@Named(WEATHER_CLIENT) okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:/api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @FlowScope
    fun provideWeatherApi(@Named(WEATHER_CLIENT) retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @FlowScope
    fun providesWeatherRemoteGateway(citiesApi: WeatherApi): WeatherRemoteGateway =
        WeatherRemoteGateway(citiesApi)

    companion object {
        private const val WEATHER_CLIENT = "WEATHER_CLIENT"
        private const val TIMEOUT_SECONDS = 60L
    }
}
