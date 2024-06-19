package com.example.flightsearchapp.core.testing.repository

import com.example.flightsearchapp.core.data.datasource.AirportsFtsDataSource
import com.example.flightsearchapp.core.testing.fake.data.database.airportFtsEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.datasource.AirportsFtsFakeDataSource
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
