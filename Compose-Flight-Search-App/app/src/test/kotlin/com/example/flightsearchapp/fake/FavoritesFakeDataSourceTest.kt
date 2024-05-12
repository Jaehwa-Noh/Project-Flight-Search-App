package com.example.flightsearchapp.fake

import com.example.flightsearchapp.testing.model.database.airportEntitiesTestData
import com.example.flightsearchapp.testing.model.database.favoriteEntitiesTestData
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull

class FavoritesFakeDataSourceTest {
    private lateinit var favoritesFakeDataSource: FavoritesFakeDataSource

    @BeforeTest
    fun setUp() {
        val airportsFakeDataSource = AirportsFakeDataSource()
        airportsFakeDataSource.insertAirportsEntities(airportEntitiesTestData)

        favoritesFakeDataSource = FavoritesFakeDataSource(
            airportsFakeDataSource = airportsFakeDataSource,
        )
    }

    @Test
    fun favorites_getFavoriteWithAirports_matchResult() = runTest {
        favoriteEntitiesTestData.forEach {
            favoritesFakeDataSource.insertFavorite(it)
        }

        val favoriteWithAirports = favoritesFakeDataSource.getFavoriteWithAirports().first()
        val result = favoriteWithAirports.filter {
            it.arriveAirport.iataCode == "CDE"
        }

        assertEquals(1, result.count())
    }

    @Test
    fun null_getFavoriteWithAirports_null() = runTest {
        val favoriteWithAirports = withTimeoutOrNull(1000) {
            favoritesFakeDataSource.getFavoriteWithAirports().first()
        }

        val result = favoriteWithAirports?.filter {
            it.arriveAirport.iataCode == "CDE"
        }

        assertNull(result?.count())
    }

    @Test
    fun favorites_getFavoriteWithAirports_matchCount() = runTest {
        favoriteEntitiesTestData.forEach {
            favoritesFakeDataSource.insertFavorite(it)
        }

        val favoriteWithAirports = favoritesFakeDataSource.getFavoriteWithAirports().first()
        val result = favoriteWithAirports.filter {
            it.arriveAirport.iataCode == "Ich"
        }

        assertEquals(0, result.count())
    }
}
