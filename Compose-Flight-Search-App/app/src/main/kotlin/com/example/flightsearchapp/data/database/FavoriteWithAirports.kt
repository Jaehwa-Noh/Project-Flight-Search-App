package com.example.flightsearchapp.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.flightsearchapp.ui.model.Flight

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

fun FavoriteWithAirports.asFlight(): Flight = Flight(
    id = "${departureAirport.id}_${arriveAirport.id}",
    departureIataCode = departureAirport.iataCode,
    departureName = departureAirport.name,
    arriveIataCode = arriveAirport.iataCode,
    arriveName = arriveAirport.name,
    isBookmarked = true
)


