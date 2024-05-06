package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.AirportsDataSource
import com.example.flightsearchapp.testing.model.database.airportEntitiesTestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Instrumented test for [AirportsFakeDataSource].
 */
class AirportsFakeDataSourceTest {
    lateinit var airportsDataSource: AirportsDataSource

    @Before
    fun setUp() {
        airportsDataSource = AirportsFakeDataSource()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fakeData_run_allMethodDone() = runTest {
        (airportsDataSource as AirportsFakeDataSource).insertAirportsEntities(
            airportEntitiesTestData)

        val getAirportByCode = airportsDataSource.getAirportByCode("GHI")
        advanceUntilIdle()
        assertTrue { getAirportByCode.iataCode == "GHI" }

        val getAirportsStream = airportsDataSource.getAirportsStream(1)
        assertEquals(airportEntitiesTestData.first().name, getAirportsStream.first().first().name)

        val getAllAirportsEntities = airportsDataSource.getAllAirportsEntities()
        assertEquals(airportEntitiesTestData, getAllAirportsEntities)

        val getAirportsByCodeOrNameStream = airportsDataSource.getAirportsByCodeOrNameStream("CDE")
        assertEquals("CDE", getAirportsByCodeOrNameStream.first().first().iataCode)

        val getAirportNullByIdStream = airportsDataSource.getAirportNullByIdStream(9)
        assertEquals(null, getAirportNullByIdStream.firstOrNull())
    }
}
