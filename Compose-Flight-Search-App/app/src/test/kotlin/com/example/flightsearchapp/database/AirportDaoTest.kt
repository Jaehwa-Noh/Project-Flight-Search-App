package com.example.flightsearchapp.database

import androidx.room.Room
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.testing.model.database.airportEntitiesTestData
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Instrumented test for [AirportDao]
 */
@RunWith(RobolectricTestRunner::class)
class AirportDaoTest {

    private lateinit var airportDao: AirportDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDB() {
        val context = RuntimeEnvironment
            .getApplication()
            .applicationContext

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        airportDao = appDatabase.airportDao()

        runTest {
            airportDao.insertAll(airportEntitiesTestData)
        }
    }

    @After
    fun closeDB() {
        appDatabase.close()
    }

    @Test
    fun searchQuery_searchName1_matchIataCode() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "name1").first()

        assertEquals(
            listOf("ABC"),
            searchedAirport.map {
                it.iataCode
            },
        )
    }

    @Test
    fun searchQuery_searchKOR_notFoundKORAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "KOR").first()

        assertEquals(
            emptyList(),
            searchedAirport.map {
                it.iataCode
            },
        )
    }

    @Test
    fun searchQuery_searchName2_matchName() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "name2").first()

        assertTrue {
            searchedAirport
                .first()
                .name
                .lowercase()
                .contains("name2")
        }
    }

    @Test
    fun searchQuery_searchKorea_notFoundKoreaAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "Korea").first()

        assertEquals(
            emptyList(),
            searchedAirport.map {
                it.name
            },
        )
    }

    @Test
    fun searchQuery_rightAirportId_allAirportsBesidesItself() = runTest {
        val allAirportsExceptItself = airportDao.getAirportsStream(airportId = 1L).first()

        allAirportsExceptItself.forEach { airportEntity ->
            assertTrue {
                1L != airportEntity.id
            }
        }
    }

    @Test
    fun searchQuery_wrongAirportId_null() = runTest {
        val airport = airportDao.getAirportNullByIdStream(airportId = -1L).firstOrNull()

        assertNull(airport)
    }

    @Test
    fun searchQuery_rightAirportId_notNUll() = runTest {
        val airport = airportDao.getAirportNullByIdStream(airportId = 1L).firstOrNull()

        assertNotNull(airport)
    }
}
