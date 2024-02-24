package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.AirportsDataSource
import com.example.flightsearchapp.data.LocalAirportsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AirportsDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindAirportsDataSourceModule(impl: LocalAirportsDataSource): AirportsDataSource
}