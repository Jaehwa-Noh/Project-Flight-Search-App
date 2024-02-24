package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.InDiskAirportsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AirportsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAirportRepository(impl: InDiskAirportsRepository): AirportsRepository
}