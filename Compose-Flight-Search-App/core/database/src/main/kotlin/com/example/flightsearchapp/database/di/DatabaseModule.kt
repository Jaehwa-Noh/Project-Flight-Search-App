package com.example.flightsearchapp.database.di

import android.content.Context
import androidx.room.Room
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
    fun provideDatabase(@ApplicationContext context: Context): com.example.flightsearchapp.database.dao.AppDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context,
                com.example.flightsearchapp.database.dao.AppDatabase::class.java,
                "app_database",
            )
                .createFromAsset("database/flight_search.db")
                .build()
        }
    }
}
