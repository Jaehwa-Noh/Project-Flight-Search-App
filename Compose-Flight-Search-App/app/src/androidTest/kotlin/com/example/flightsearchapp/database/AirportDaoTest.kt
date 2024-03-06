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
    fun whenSearch_HAM_FoundHAMAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "HAM").first()

        assertEquals(
            listOf("HAM"),
            searchedAirport.map {
                it.iataCode
            },
        )
    }

    @Test
    fun whenSearch_KOR_NotFoundKORAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "KOR").first()

        assertEquals(
            emptyList(),
            searchedAirport.map {
                it.iataCode
            },
        )
    }

    @Test
    fun whenSearch_Paris_FoundParisAirport() = runTest {
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
    fun whenSearch_Korea_NotFoundKoreaAirport() = runTest {
        val searchedAirport = airportDao.getAirportsByCodeOrNameStream(query = "Korea").first()

        assertEquals(
            emptyList(),
            searchedAirport.map {
                it.name
            }
        )
    }

    @Test
    fun airportId_WhenRightAirportId_AllAirportsBesidesIt() = runTest {
        val allAirportsExceptItself = airportDao.getAirportsStream(airportId = 1L).first()

        allAirportsExceptItself.forEach { airportEntity ->
            assertTrue {
                1L != airportEntity.id
            }
        }
    }

    @Test
    fun airportId_WhenWrongAirportId_Null() = runTest {
        val airport = airportDao.getAirportNullByIdStream(airportId = -1L).firstOrNull()

        assertNull(airport)
    }

    @Test
    fun airportId_WhenRightAirportId_FoundAirport() = runTest {
        val airport = airportDao.getAirportNullByIdStream(airportId = 1L).firstOrNull()

        assertNotNull(airport)
    }
}
