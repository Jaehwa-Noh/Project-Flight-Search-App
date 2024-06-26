package com.example.flightsearchapp.core.testing.datasource

import com.example.flightsearchapp.core.testing.fake.data.database.airportEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.data.database.favoriteEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.datasource.AirportsFakeDataSource
import com.example.flightsearchapp.core.testing.fake.datasource.FavoritesFakeDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
        val favoriteWithAirports = favoritesFakeDataSource.getFavoriteWithAirports().first()

        val result = favoriteWithAirports.filter {
            it.arriveAirport.iataCode == "CDE"
        }

        assertEquals(result.count(), 0)
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
