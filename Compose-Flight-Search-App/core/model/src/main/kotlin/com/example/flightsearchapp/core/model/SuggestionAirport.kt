package com.example.flightsearchapp.core.model

data class SuggestionAirport(
    val id: Long,
    val iataCode: String,
    val name: String,
    val passengers: Long,
)
