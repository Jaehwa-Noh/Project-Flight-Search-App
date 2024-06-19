package com.example.flightsearchapp.core.testing.fake.ui

import com.example.flightsearchapp.core.model.Flight

val flightTestData = Flight(
    id = "departure_arrive",
    departureIataCode = "ICN",
    departureName = "Incheon",
    arriveIataCode = "CDG",
    arriveName = "Paris airport",
    isBookmarked = false,
)

fun Flight.bookMarkToggle() =
    Flight(
        id,
        departureName,
        departureIataCode,
        arriveIataCode,
        arriveName,
        !isBookmarked,
    )
