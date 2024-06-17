package com.example.flightsearchapp.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideAirportDao(database: com.example.flightsearchapp.database.dao.AppDatabase): com.example.flightsearchapp.database.dao.AirportDao = database.airportDao()

    @Provides
    fun provideFavoriteDao(database: com.example.flightsearchapp.database.dao.AppDatabase): com.example.flightsearchapp.database.dao.FavoriteDao = database.favoriteDao()

    @Provides
    fun provideAirportFtsDao(database: com.example.flightsearchapp.database.dao.AppDatabase): com.example.flightsearchapp.database.dao.AirportFtsDao = database.airportFtsDao()
}
