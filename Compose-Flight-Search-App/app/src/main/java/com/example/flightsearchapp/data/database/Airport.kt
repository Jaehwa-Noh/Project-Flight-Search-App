package com.example.flightsearchapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "iata_code")
    val iataCode: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val passengers: Long,
)