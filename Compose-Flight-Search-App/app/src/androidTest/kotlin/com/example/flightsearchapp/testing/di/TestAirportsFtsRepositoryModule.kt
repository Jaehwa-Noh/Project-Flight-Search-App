package com.example.flightsearchapp.testing.di

import com.example.flightsearchapp.data.AirportsFtsRepository
import com.example.flightsearchapp.di.AirportsFtsRepositoryModule
import com.example.flightsearchapp.testing.repository.AirportsFtsFakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AirportsFtsRepositoryModule::class],
)
abstract class TestAirportsFtsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAirportsFtsRepository(
        airportsFtsFakeRepository: AirportsFtsFakeRepository,
    ): AirportsFtsRepository
}
