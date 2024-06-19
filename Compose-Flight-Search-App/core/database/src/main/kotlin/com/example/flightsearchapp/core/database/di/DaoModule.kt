package com.example.flightsearchapp.core.database.di

import com.example.flightsearchapp.core.database.dao.AirportDao
import com.example.flightsearchapp.core.database.dao.AirportFtsDao
import com.example.flightsearchapp.core.database.dao.AppDatabase
import com.example.flightsearchapp.core.database.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideAirportDao(database: AppDatabase): AirportDao = database.airportDao()

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao = database.favoriteDao()

    @Provides
    fun provideAirportFtsDao(database: AppDatabase): AirportFtsDao = database.airportFtsDao()
}
