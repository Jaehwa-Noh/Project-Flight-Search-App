package com.example.flightsearchapp.di

import android.content.Context
import androidx.room.Room
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.data.database.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            )
                .createFromAsset("database/flight_search.db")
                .build()
        }
    }

    @Provides
    fun provideAirportDao(database: AppDatabase): AirportDao {
        return database.airportDao()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}