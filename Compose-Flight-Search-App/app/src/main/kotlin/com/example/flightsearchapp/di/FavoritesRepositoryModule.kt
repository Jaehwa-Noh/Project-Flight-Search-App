package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.FavoritesRepository
import com.example.flightsearchapp.data.InDiskFavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(impl: InDiskFavoritesRepository): FavoritesRepository

}