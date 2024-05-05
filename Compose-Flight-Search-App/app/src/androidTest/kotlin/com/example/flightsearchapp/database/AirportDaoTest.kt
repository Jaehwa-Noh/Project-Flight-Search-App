package com.example.flightsearchapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.data.database.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AirportDaoTest {

    private lateinit var airportDao: AirportDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "test_database")
            .createFromAsset("database/flight_search.db")
            .build()

        airportDao = appDatabase.airportDao()
    }

    @After
    fun closeDB() {
        appDatabase.close()
    }

    @Test
    fun searchQuery_searchHAM_foundHAMAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "HAM").first()

        assertEquals(
            listOf("HAM"),
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
    fun searchQuery_searchParis_foundParisAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "paris").first()

        assertTrue {
            searchedAirport
                .first()
                .name
                .lowercase()
                .contains("paris")
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
    fun searchQuery_rightAirportId_allAirportsBesidesIt() = runTest {
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
    fun searchQuery_rightAirportId_foundAirport() = runTest {
        val airport = airportDao.getAirportNullByIdStream(airportId = 1L).firstOrNull()

        assertNotNull(airport)
    }
}
