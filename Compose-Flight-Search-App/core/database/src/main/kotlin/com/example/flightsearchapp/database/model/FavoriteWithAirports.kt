package com.example.flightsearchapp.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteWithAirports(
    @Embedded
    val favoriteEntity: FavoriteEntity,

    @Relation(
        parentColumn = "departure_code",
        entityColumn = "iata_code",
    )
    val departureAirport: AirportEntity,

    @Relation(
        parentColumn = "destination_code",
        entityColumn = "iata_code",
    )
    val arriveAirport: AirportEntity,
)
