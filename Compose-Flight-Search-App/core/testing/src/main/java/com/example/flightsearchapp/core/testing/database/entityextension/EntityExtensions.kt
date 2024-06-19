package com.example.flightsearchapp.core.testing.database.entityextension

import com.example.flightsearchapp.core.database.model.AirportEntity

fun testAirportEntity(id: Long, code: String) =
    AirportEntity(
        id = id,
        name = "name$id",
        iataCode = code,
        passengers = id,
    )
