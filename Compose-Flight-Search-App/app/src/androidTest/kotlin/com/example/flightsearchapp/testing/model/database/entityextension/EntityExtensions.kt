package com.example.flightsearchapp.testing.model.database.entityextension

import com.example.flightsearchapp.data.database.AirportEntity

fun testAirportEntity(id: Long, code: String) = AirportEntity(
    id = id,
    name = "name$id",
    iataCode = code,
    passengers = id,
)
