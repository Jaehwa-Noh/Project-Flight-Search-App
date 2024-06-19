package com.example.flightsearchapp.core.testing.fake.di

import com.example.flightsearchapp.core.data.di.AirportsFtsRepositoryModule
import com.example.flightsearchapp.core.data.repository.AirportsFtsRepository
import com.example.flightsearchapp.core.testing.fake.repository.AirportsFtsFakeRepository
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
