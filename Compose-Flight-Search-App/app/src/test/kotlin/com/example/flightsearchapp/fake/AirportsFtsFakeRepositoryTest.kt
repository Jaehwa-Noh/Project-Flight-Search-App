package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.AirportsFtsDataSource
import com.example.flightsearchapp.testing.model.database.airportFtsEntitiesTestData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Instrumented test for [AirportsFtsFakeDataSource].
 */
class AirportsFtsFakeRepositoryTest {
    private lateinit var airportsFtsFakeDataSource: AirportsFtsDataSource

    @BeforeTest
    fun setUp() {
        airportsFtsFakeDataSource = AirportsFtsFakeDataSource()
    }

    @Test
    fun airportsFts_upsertAndSearch_matchCount() = runTest {
        airportsFtsFakeDataSource.upsertAirports(airportFtsEntitiesTestData)
        val result = airportsFtsFakeDataSource.searchAirportsStream("HAM").first()

        assertEquals(1, result.count())
    }

    @Test
    fun airportsFts_deleteAndInsertAllAndSearch_matchCount() = runTest {
        airportsFtsFakeDataSource.upsertAirports(airportFtsEntitiesTestData)
        var result = airportsFtsFakeDataSource.searchAirportsStream("HAM").first()

        assertEquals(1, result.count())

        airportsFtsFakeDataSource.deleteAndInsertAll(airportFtsEntitiesTestData)
        result = airportsFtsFakeDataSource.searchAirportsStream("CDE").first()

        assertEquals(1, result.count())
    }
}
