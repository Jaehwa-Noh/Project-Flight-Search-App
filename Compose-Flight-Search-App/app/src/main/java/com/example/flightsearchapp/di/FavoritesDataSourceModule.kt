package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.FavoritesDataSource
import com.example.flightsearchapp.data.LocalFavoritesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindFavoritesDataSource(impl: LocalFavoritesDataSource): FavoritesDataSource

}