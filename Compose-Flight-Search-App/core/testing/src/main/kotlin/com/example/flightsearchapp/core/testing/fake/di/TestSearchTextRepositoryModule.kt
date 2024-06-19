package com.example.flightsearchapp.core.testing.fake.di

import com.example.flightsearchapp.core.data.di.SearchTextRepositoryModule
import com.example.flightsearchapp.core.data.repository.SearchTextRepository
import com.example.flightsearchapp.core.testing.fake.repository.SearchTextFakeHiltRepository
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
    abstract fun bindSearchTextRepository(impl: SearchTextFakeHiltRepository): SearchTextRepository
}
