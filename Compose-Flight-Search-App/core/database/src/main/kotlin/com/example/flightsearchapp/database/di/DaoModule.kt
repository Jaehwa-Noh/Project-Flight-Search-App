package com.example.flightsearchapp.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideAirportDao(database: com.example.flightsearchapp.database.database.AppDatabase): com.example.flightsearchapp.database.database.AirportDao = database.airportDao()

    @Provides
    fun provideFavoriteDao(database: com.example.flightsearchapp.database.database.AppDatabase): com.example.flightsearchapp.database.database.FavoriteDao = database.favoriteDao()

    @Provides
    fun provideAirportFtsDao(database: com.example.flightsearchapp.database.database.AppDatabase): com.example.flightsearchapp.database.database.AirportFtsDao = database.airportFtsDao()
}
