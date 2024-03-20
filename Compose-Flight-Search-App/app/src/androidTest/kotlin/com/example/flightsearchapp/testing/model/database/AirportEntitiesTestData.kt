package com.example.flightsearchapp.testing.model.database

import com.example.flightsearchapp.data.database.AirportEntity
import com.example.flightsearchapp.testing.model.database.entityextension.testAirportEntity

val airportEntitiesTestData: List<AirportEntity> = listOf(
    testAirportEntity(1, "ABC"),
    testAirportEntity(2, "CDE"),
    testAirportEntity(3, "EFG"),
    testAirportEntity(4, "GHI"),
)
