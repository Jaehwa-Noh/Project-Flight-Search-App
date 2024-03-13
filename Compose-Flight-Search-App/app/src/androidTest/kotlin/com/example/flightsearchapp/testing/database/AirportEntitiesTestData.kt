package com.example.flightsearchapp.testing.database

import com.example.flightsearchapp.data.database.AirportEntity

val airportEntitiesTestData: List<AirportEntity> = listOf(
    testFavoriteEntity(1, "ABC"),
    testFavoriteEntity(2, "CDE"),
    testFavoriteEntity(3, "EFG"),
    testFavoriteEntity(4, "GHI"),
)

private fun testFavoriteEntity(id: Long, code: String) = AirportEntity(
    id = id,
    name = "name$id",
    iataCode = code,
    passengers = id,
)
