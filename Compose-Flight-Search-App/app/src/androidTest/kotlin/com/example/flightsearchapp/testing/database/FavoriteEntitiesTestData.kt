package com.example.flightsearchapp.testing.database

import com.example.flightsearchapp.data.database.FavoriteEntity

val favoriteEntitiesTestData: List<FavoriteEntity> = listOf(
    testFavoriteEntity(0),
    testFavoriteEntity(1),
    testFavoriteEntity(2),
    testFavoriteEntity(3),
)

private fun testFavoriteEntity(id: Long) = FavoriteEntity(
    id = id,
    departureCode = "departureCode$id",
    destinationCode = "destinationCode$id",
)
