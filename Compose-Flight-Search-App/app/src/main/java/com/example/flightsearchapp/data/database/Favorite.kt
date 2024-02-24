package com.example.flightsearchapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "departure_code")
    val departureCode: String,

    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)
