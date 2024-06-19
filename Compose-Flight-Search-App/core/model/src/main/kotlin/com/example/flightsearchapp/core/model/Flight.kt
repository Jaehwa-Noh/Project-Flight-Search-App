package com.example.flightsearchapp.core.model

data class Flight(
    val id: String,
    val departureIataCode: String,
    val departureName: String,
    val arriveIataCode: String,
    val arriveName: String,
    val isBookmarked: Boolean,
)
