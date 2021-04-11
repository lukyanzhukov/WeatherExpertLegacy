package com.lukianbat.weatherexpertlegacy.di.module

import android.content.Context
import android.util.Log
import com.lukianbat.architecture.mvvm.RxViewOutput
import com.lukianbat.architecture.network.NetworkLoggingInterceptor
import com.lukianbat.prefser.Prefser
import com.lukianbat.weatherexpertlegacy.utils.DefaultErrorAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
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
    fun provideErrorAdapter(@ApplicationContext context: Context): RxViewOutput.ErrorAdapter {
        return DefaultErrorAdapter(context)
    }

    @Provides
    @Singleton
    fun providePrefser(@ApplicationContext context: Context): Prefser = Prefser(context)

    companion object {
        private const val NETWORK_LOG_TAG = "NETWORK_LOG"
        private const val CONNECT_TIMEOUT = 10000L
        private const val READ_WRITE_TIMEOUT = 30000L
    }
}