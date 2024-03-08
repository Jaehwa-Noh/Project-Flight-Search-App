package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.AirportsDataSource
import com.example.flightsearchapp.data.AirportsFtsDataSource
import com.example.flightsearchapp.data.FavoritesDataSource
import com.example.flightsearchapp.data.LocalAirportsDataSource
import com.example.flightsearchapp.data.LocalFavoritesDataSource
import dagger.Binds
import javax.inject.Inject
import javax.inject.Singleton

abstract class DataSourceModule @Inject constructor() {
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
