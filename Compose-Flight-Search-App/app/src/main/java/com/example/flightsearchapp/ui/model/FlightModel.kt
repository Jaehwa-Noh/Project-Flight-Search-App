package com.example.flightsearchapp.ui.model

data class FlightModel(
    val id: String,
    val departureId: Long,
    val departureIataCode: String,
    val departureName: String,
    val arriveId: Long,
    val arriveIataCode: String,
    val arriveName: String,
)