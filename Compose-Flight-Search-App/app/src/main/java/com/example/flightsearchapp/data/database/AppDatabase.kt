package com.example.flightsearchapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [AirportEntity::class, FavoriteEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
}