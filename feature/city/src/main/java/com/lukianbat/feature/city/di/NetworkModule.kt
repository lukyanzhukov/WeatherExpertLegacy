package com.lukianbat.feature.city.di

import com.lukianbat.feature.city.data.remote.CitiesApi
import com.lukianbat.feature.city.data.remote.CitiesInterceptor
import com.lukianbat.feature.city.data.remote.gateway.CitiesRemoteGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module
class NetworkModule {

    @Provides
    @Named(CITIES_HTTP_CLIENT)
    fun provideCitiesOkHttpClient(
        okHttpClient: OkHttpClient,
        authInterceptor: CitiesInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideCitiesRetrofit(@Named(CITIES_HTTP_CLIENT) okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:/wft-geo-db.p.rapidapi.com/v1/geo/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    fun provideCitiesApi(retrofit: Retrofit): CitiesApi = retrofit.create(CitiesApi::class.java)

    @Provides
    fun providesCitiesGateway(citiesApi: CitiesApi): CitiesRemoteGateway = CitiesRemoteGateway(citiesApi)

    companion object {
        private const val CITIES_HTTP_CLIENT = "CITIES_HTTP_CLIENT"
    }
}
