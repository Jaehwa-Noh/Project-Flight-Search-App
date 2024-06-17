package com.example.flightsearchapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class AirportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "iata_code")
    val iataCode: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val passengers: Long,
)

fun AirportEntity.asFtsEntity() = AirportFtsEntity(
    iataCode = iataCode,
    name = name,
)

fun AirportEntity.asSuggestionAirport() =
    SuggestionAirport(
        id = id,
        iataCode = iataCode,
        name = name,
        passengers = passengers,
    )
