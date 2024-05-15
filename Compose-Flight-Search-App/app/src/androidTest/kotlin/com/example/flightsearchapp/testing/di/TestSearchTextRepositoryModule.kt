package com.example.flightsearchapp.testing.di

import com.example.flightsearchapp.data.SearchTextRepository
import com.example.flightsearchapp.di.SearchTextRepositoryModule
import com.example.flightsearchapp.testing.repository.SearchTextFakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchTextRepositoryModule::class],
)
abstract class TestSearchTextRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchTextRepository(impl: SearchTextFakeRepository): SearchTextRepository
}
