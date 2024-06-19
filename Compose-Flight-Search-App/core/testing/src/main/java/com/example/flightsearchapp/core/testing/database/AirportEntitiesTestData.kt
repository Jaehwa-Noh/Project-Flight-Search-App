package com.example.flightsearchapp.core.testing.database

import com.example.flightsearchapp.core.database.model.AirportEntity

/**
 * Test data for List<[AirportEntity]>.
 */
val airportEntitiesTestData: List<AirportEntity> = listOf(
    testAirportEntity(1, "ABC"),
    testAirportEntity(2, "CDE"),
    testAirportEntity(3, "EFG"),
    testAirportEntity(4, "GHI"),
)

/**
 * Create test data for [AirportEntity].
 */
fun testAirportEntity(id: Long, code: String) =
    AirportEntity(
        id = id,
        name = "name$id",
        iataCode = code,
        passengers = 10 - id,
    )
