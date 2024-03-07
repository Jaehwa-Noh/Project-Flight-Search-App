package com.example.flightsearchapp.di

import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.data.database.AirportFtsDao
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.data.database.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideAirportDao(database: AppDatabase): AirportDao =
        database.airportDao()


    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao =
        database.favoriteDao()

    @Provides
    fun provideAirportFtsDao(database: AppDatabase): AirportFtsDao =
        database.airportFtsDao()

}

