package com.eloyan.weathereloyan.di

import android.content.Context
import com.eloyan.weathereloyan.model.location.LocationRepository
import com.eloyan.weathereloyan.model.location.LocationRepositoryImpl
import com.eloyan.weathereloyan.network.ApiWeatherInterface
import com.eloyan.weathereloyan.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleObject {

    @Singleton
    @Provides
    fun provideLocationRepository(@ApplicationContext appContext: Context): LocationRepositoryImpl {
        return LocationRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideApiWeatherInterface(retrofit: Retrofit): ApiWeatherInterface =
        RetrofitClient.client.create(ApiWeatherInterface::class.java)
}