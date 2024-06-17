package com.example.flightsearchapp.core.data.di

import com.example.flightsearchapp.core.data.repository.AirportsFtsRepository
import com.example.flightsearchapp.core.data.repository.AirportsRepository
import com.example.flightsearchapp.core.data.repository.FavoritesRepository
import com.example.flightsearchapp.core.data.repository.InDiskAirportsFtsRepository
import com.example.flightsearchapp.core.data.repository.InDiskAirportsRepository
import com.example.flightsearchapp.core.data.repository.InDiskFavoritesRepository
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
