package com.example.flightsearchapp.testing.database

import com.example.flightsearchapp.data.database.FavoriteEntity

val favoriteEntitiesTestData: List<FavoriteEntity> = listOf(
    testFavoriteEntity(1, "ABC", "CDE"),
    testFavoriteEntity(2, "BCD", "DEF"),
    testFavoriteEntity(3, "CDE", "EFG"),
    testFavoriteEntity(4, "EFG", "GHI"),
)

private fun testFavoriteEntity(
    id: Long,
    departureCode: String,
    destinationCode: String,
) = FavoriteEntity(
    id = id,
    departureCode = departureCode,
    destinationCode = destinationCode,
)
