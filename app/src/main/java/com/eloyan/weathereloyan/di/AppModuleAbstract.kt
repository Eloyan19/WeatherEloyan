package com.eloyan.weathereloyan.di

import com.eloyan.weathereloyan.model.location.LocationRepository
import com.eloyan.weathereloyan.model.location.LocationRepositoryImpl
import com.eloyan.weathereloyan.model.weather.WeatherRepository
import com.eloyan.weathereloyan.model.weather.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleAbstract {

    @Binds
    abstract fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindLocationRepository(locationRepository: LocationRepositoryImpl): LocationRepository
}