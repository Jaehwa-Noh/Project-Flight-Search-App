package com.example.flightsearchapp.ui.model

fun com.example.flightsearchapp.database.model.FavoriteWithAirports.asFlight(): com.example.flightsearchapp.feature.searchscreen.ui.model.Flight =
    com.example.flightsearchapp.feature.searchscreen.ui.model.Flight(
        id = "${departureAirport.id}_${arriveAirport.id}",
        departureIataCode = departureAirport.iataCode,
        departureName = departureAirport.name,
        arriveIataCode = arriveAirport.iataCode,
        arriveName = arriveAirport.name,
        isBookmarked = true,
    )
