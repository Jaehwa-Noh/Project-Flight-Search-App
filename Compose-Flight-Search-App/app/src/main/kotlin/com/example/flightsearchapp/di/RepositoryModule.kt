package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.FavoritesRepository
import com.example.flightsearchapp.data.InDiskAirportsRepository
import com.example.flightsearchapp.data.InDiskFavoritesRepository
import dagger.Binds
import javax.inject.Inject
import javax.inject.Singleton

abstract class RepositoryModule @Inject constructor() {
    @Singleton
    @Binds
    abstract fun bindAirportRepository(impl: InDiskAirportsRepository): AirportsRepository

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(impl: InDiskFavoritesRepository): FavoritesRepository
}
