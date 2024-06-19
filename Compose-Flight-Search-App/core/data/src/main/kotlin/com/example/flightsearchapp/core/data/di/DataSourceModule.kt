package com.example.flightsearchapp.core.data.di

import com.example.flightsearchapp.core.data.datasource.AirportsDataSource
import com.example.flightsearchapp.core.data.datasource.AirportsFtsDataSource
import com.example.flightsearchapp.core.data.datasource.FavoritesDataSource
import com.example.flightsearchapp.core.data.datasource.LocalAirportsDataSource
import com.example.flightsearchapp.core.data.datasource.LocalAirportsFtsDataSource
import com.example.flightsearchapp.core.data.datasource.LocalFavoritesDataSource
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
    abstract fun bindAirportsFtsDataSource(impl: LocalAirportsFtsDataSource): AirportsFtsDataSource
}
