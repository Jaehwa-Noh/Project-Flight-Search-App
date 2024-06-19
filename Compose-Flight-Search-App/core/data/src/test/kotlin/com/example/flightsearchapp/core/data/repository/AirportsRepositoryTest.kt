package com.example.flightsearchapp.core.data.repository

import com.example.flightsearchapp.core.testing.fake.data.database.airportEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.datasource.AirportsFakeDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Instrumented test for [AirportsRepository]
 */
class AirportsRepositoryTest {

    private lateinit var airportsRepository: AirportsRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        val airportsDataSource =
            AirportsFakeDataSource()

        airportsDataSource.insertAirportsEntities(
            airportEntitiesTestData,
        )

        airportsRepository = InDiskAirportsRepository(
            airportsDataSource = airportsDataSource,
            defaultDispatcher = testDispatcher,
        )
    }

    @Test
    fun searchQuery_search_matchSuggestion() = runTest {
        val result = airportsRepository.getSuggestionsStream("ABC").first().first()

        assertEquals(result.iataCode, "ABC")
    }

    @Test
    fun searchQuery_search_matchAirports() = runTest {
        val result = airportsRepository.getAirportsByCodeOrNameStream("ABC").first().first()

        assertEquals(result.iataCode, "ABC")
    }

    @Test
    fun airportId_search_matchAirport() = runTest {
        val result = airportsRepository.getAirportNullByIdStream(1).first()

        assertEquals(result?.iataCode, "ABC")
    }

    @Test
    fun airportId_search_matchAirports() = runTest {
        val result = airportsRepository.getAirportsStream(1).first().first()

        assertEquals(result.iataCode, "ABC")
    }

    @Test
    fun airportCode_search_matchAirport() = runTest {
        val result = airportsRepository.getAirportByCode("CDE")

        assertEquals(result.iataCode, "CDE")
    }

    @Test
    fun airport_getAll_matchAirports() = runTest {
        val result = airportsRepository.getAllAirportsEntities()
        assertTrue { result.size == airportEntitiesTestData.size }
    }
}
