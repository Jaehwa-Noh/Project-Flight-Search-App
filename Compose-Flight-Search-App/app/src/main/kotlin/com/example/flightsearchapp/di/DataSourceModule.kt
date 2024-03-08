package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.AirportsDataSource
import com.example.flightsearchapp.data.AirportsFtsDataSource
import com.example.flightsearchapp.data.FavoritesDataSource
import com.example.flightsearchapp.data.LocalAirportsDataSource
import com.example.flightsearchapp.data.LocalFavoritesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindAirportsDataSourceModule(impl: LocalAirportsDataSource): AirportsDataSource

    @Singleton
    @Binds
    abstract fun bindFavoritesDataSource(impl: LocalFavoritesDataSource): FavoritesDataSource

    @Singleton
    @Binds
    abstract fun bindAirportsFtsDataSource(impl: AirportsFtsDataSource): AirportsFtsDataSource
}
