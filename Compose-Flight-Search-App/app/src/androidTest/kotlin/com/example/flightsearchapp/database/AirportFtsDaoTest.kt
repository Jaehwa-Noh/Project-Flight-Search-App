package com.example.flightsearchapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.data.database.AirportFtsDao
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.data.database.asFtsEntity
import com.example.flightsearchapp.testing.model.database.airportEntitiesTestData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Instrumented test for [AirportFtsDao]
 */
class AirportFtsDaoTest {

    private lateinit var airportFtsDao: AirportFtsDao
    private lateinit var airportDao: AirportDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDB() {
        val context: Context = ApplicationProvider.getApplicationContext()

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        airportDao = appDatabase.airportDao()
        airportFtsDao = appDatabase.airportFtsDao()

        runTest {
            airportDao.insertAll(airportEntitiesTestData)
        }
    }

    @After
    fun closeDB() = appDatabase.close()

    @Test
    fun airportEntities_searchNotExist_empty() = runTest {
        upsertEntities(this)
        val result = airportFtsDao.searchAirportsStream(" ").first()

        assertEquals(emptyList(), result)
    }

    @Test
    fun airportEntities_searchABC_matchResult() = runTest {
        upsertEntities(this)
        val result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals("ABC", result.first())
    }

    @Test
    fun airportEntities_delete_empty() = runTest {
        upsertEntities(this)
        var result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals("ABC", result.first())

        airportFtsDao.deleteAll()
        result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals(emptyList(), result)
    }

    @Test
    fun airportEntities_deleteAndUpsert_matchResult() = runTest {
        upsertEntities(this)
        var result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals("ABC", result.first())

        airportFtsDao.deleteAll()
        result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals(emptyList(), result)

        airportFtsDao.deleteAndInsertAll(getAirportFtsEntities())
        result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals("ABC", result.first())
    }

    private fun getAirportFtsEntities() = airportEntitiesTestData.map {
        it.asFtsEntity()
    }

    private fun upsertEntities(testScope: CoroutineScope) {
        val ftsEntities = getAirportFtsEntities()
        testScope.launch {
            airportFtsDao.upsertAirports(ftsEntities)
        }
    }
}
