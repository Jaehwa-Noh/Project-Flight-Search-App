package com.example.flightsearchapp.ui.model

data class FlightModel(
    val id: String,
    val departureIataCode: String,
    val departureName: String,
    val arriveIataCode: String,
    val arriveName: String,
    val isBookmarked: Boolean,
)