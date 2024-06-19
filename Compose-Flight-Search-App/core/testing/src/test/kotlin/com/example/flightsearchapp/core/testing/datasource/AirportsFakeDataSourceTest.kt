package com.example.flightsearchapp.core.testing.datasource

import com.example.flightsearchapp.core.data.datasource.AirportsDataSource
import com.example.flightsearchapp.core.testing.fake.data.database.airportEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.datasource.AirportsFakeDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Instrumented test for [AirportsFakeDataSource].
 */
class AirportsFakeDataSourceTest {
    private lateinit var airportsDataSource: AirportsDataSource

    @BeforeTest
    fun setUp() {
        airportsDataSource = AirportsFakeDataSource()
    }

    @Test
    fun fakeData_run_allMethodDone() = runTest {
        (airportsDataSource as AirportsFakeDataSource).insertAirportsEntities(
            airportEntitiesTestData,
        )

        val getAirportByCode = airportsDataSource.getAirportByCode("GHI")

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
