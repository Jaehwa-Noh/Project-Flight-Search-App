package com.example.flightsearchapp.core.database

import androidx.room.Room
import com.example.flightsearchapp.core.data.model.asFtsEntity
import com.example.flightsearchapp.core.database.dao.AirportDao
import com.example.flightsearchapp.core.database.dao.AirportFtsDao
import com.example.flightsearchapp.core.database.dao.AppDatabase
import com.example.flightsearchapp.core.testing.fake.data.database.airportEntitiesTestData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Instrumented test for [AirportFtsDao]
 */
@RunWith(RobolectricTestRunner::class)
class AirportFtsDaoTest {

    private lateinit var airportFtsDao: AirportFtsDao
    private lateinit var airportDao: AirportDao
    private lateinit var appDatabase: AppDatabase

    @BeforeTest
    fun createDB() {
        val context = RuntimeEnvironment
            .getApplication()
            .applicationContext

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        airportDao = appDatabase.airportDao()
        airportFtsDao = appDatabase.airportFtsDao()
    }

    @AfterTest
    fun closeDB() = appDatabase.close()

    @Test
    fun airportEntities_searchNotExist_empty() = runTest {
        initDb()
        val result = airportFtsDao.searchAirportsStream(" ").first()

        assertEquals(emptyList(), result)
    }

    @Test
    fun airportEntities_searchABC_matchResult() = runTest {
        initDb()
        val result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals("ABC", result.first())
    }

    @Test
    fun airportEntities_delete_empty() = runTest {
        initDb()
        var result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals("ABC", result.first())

        airportFtsDao.deleteAll()
        result = airportFtsDao.searchAirportsStream("ABC").first()

        assertEquals(emptyList(), result)
    }

    @Test
    fun airportEntities_deleteAndUpsert_matchResult() = runTest {
        initDb()
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

    private suspend fun upsertEntities() {
        val ftsEntities = getAirportFtsEntities()
        airportFtsDao.upsertAirports(ftsEntities)
    }

    private suspend fun initDb() {
        airportDao.insertAll(airportEntitiesTestData)
        upsertEntities()
    }
}
