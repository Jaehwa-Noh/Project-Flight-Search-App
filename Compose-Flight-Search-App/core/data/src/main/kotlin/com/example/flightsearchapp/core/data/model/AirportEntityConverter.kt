package com.example.flightsearchapp.core.data.model

import com.example.flightsearchapp.core.database.model.AirportEntity
import com.example.flightsearchapp.core.database.model.AirportFtsEntity
import com.example.flightsearchapp.core.model.SuggestionAirport

fun AirportEntity.asFtsEntity() = AirportFtsEntity(
    iataCode = iataCode,
    name = name,
)

fun AirportEntity.asSuggestionAirport() =
    SuggestionAirport(
        id = id,
        iataCode = iataCode,
        name = name,
        passengers = passengers,
    )
