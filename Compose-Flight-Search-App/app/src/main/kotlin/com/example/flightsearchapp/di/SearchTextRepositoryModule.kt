package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.InDiskSearchTextRepository
import com.example.flightsearchapp.data.SearchTextRepository
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
