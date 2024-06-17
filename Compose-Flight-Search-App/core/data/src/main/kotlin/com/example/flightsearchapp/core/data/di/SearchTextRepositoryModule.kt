package com.example.flightsearchapp.core.data.di

import com.example.flightsearchapp.core.data.repository.InDiskSearchTextRepository
import com.example.flightsearchapp.core.data.repository.SearchTextRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchTextRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSearchTextRepository(impl: InDiskSearchTextRepository): SearchTextRepository
}
