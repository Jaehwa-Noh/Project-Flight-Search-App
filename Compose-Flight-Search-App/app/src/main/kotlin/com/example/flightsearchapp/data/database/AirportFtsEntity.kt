package com.example.flightsearchapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "airportFts")
@Fts4
data class AirportFtsEntity(
    @ColumnInfo(name = "iata_code")
    val iataCode: String,

    @ColumnInfo
    val name: String,
)
