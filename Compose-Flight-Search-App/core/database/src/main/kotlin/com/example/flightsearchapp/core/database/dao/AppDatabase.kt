package com.example.flightsearchapp.core.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightsearchapp.core.database.model.AirportEntity
import com.example.flightsearchapp.core.database.model.AirportFtsEntity
import com.example.flightsearchapp.core.database.model.FavoriteEntity

@Database(
    entities = [
        AirportEntity::class,
        FavoriteEntity::class,
        AirportFtsEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun airportFtsDao(): AirportFtsDao
}
