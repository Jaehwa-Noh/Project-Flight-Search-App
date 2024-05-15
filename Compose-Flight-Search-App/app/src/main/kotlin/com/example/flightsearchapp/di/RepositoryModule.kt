package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.AirportsFtsRepository
import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.FavoritesRepository
import com.example.flightsearchapp.data.InDiskAirportsFtsRepository
import com.example.flightsearchapp.data.InDiskAirportsRepository
import com.example.flightsearchapp.data.InDiskFavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAirportsRepository(impl: InDiskAirportsRepository): AirportsRepository

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(impl: InDiskFavoritesRepository): FavoritesRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AirportsFtsRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAirportsFtsRepository(impl: InDiskAirportsFtsRepository): AirportsFtsRepository
}
