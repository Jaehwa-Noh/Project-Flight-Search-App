package com.example.flightsearchapp.core.data.model

import com.example.flightsearchapp.core.database.model.FavoriteWithAirports
import com.example.flightsearchapp.core.model.Flight

fun FavoriteWithAirports.asFlight(): Flight =
    Flight(
        id = "${departureAirport.id}_${arriveAirport.id}",
        departureIataCode = departureAirport.iataCode,
        departureName = departureAirport.name,
        arriveIataCode = arriveAirport.iataCode,
        arriveName = arriveAirport.name,
        isBookmarked = true,
    )
