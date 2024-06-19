package com.example.flightsearchapp.core.testing.database

import com.example.flightsearchapp.core.database.model.FavoriteEntity

val favoriteEntitiesTestData: List<FavoriteEntity> = listOf(
    testFavoriteEntity(1, "ABC", "CDE"),
    testFavoriteEntity(2, "EFG", "GHI"),
)

private fun testFavoriteEntity(id: Long, departureCode: String, destinationCode: String) =
    FavoriteEntity(
        id = id,
        departureCode = departureCode,
        destinationCode = destinationCode,
    )
