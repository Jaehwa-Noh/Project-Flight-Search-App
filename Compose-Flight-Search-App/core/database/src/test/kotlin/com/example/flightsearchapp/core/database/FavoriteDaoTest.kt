package com.example.flightsearchapp.core.database

import androidx.room.Room
import com.example.flightsearchapp.core.database.dao.AirportDao
import com.example.flightsearchapp.core.database.dao.AppDatabase
import com.example.flightsearchapp.core.database.dao.FavoriteDao
import com.example.flightsearchapp.core.testing.database.airportEntitiesTestData
import com.example.flightsearchapp.core.testing.database.favoriteEntitiesTestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Instrumented test for [FavoriteDao]
 */
@RunWith(RobolectricTestRunner::class)
class FavoriteDaoTest {

    private lateinit var airportDao: AirportDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDB() {
        val context = RuntimeEnvironment
            .getApplication()
            .applicationContext

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        favoriteDao = appDatabase.favoriteDao()
        airportDao = appDatabase.airportDao()
    }

    @After
    fun closeDB() {
        appDatabase.close()
    }

    @Test
    fun favorite_getAllFavorites_allFavorites() = runTest {
        insertAllData()

        val allFavorites = favoriteDao.getAllFavoritesStream().first()

        assertEquals(favoriteEntitiesTestData, allFavorites)
    }

    @Test
    fun favorite_deleteOneFavorite_matchFavorite() = runTest {
        insertAllData()

        val deleteFavorite = favoriteEntitiesTestData.first()

        favoriteDao.delete(
            departureCode = deleteFavorite.departureCode,
            arriveCode = deleteFavorite.destinationCode,
        )

        val allFavorite = favoriteDao.getAllFavoritesStream().first()

        assertTrue(!allFavorite.contains(favoriteEntitiesTestData.first()))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchQuery_searchFavorite_matchCount() = runTest {
        airportDao.insertAll(airportEntitiesTestData)
        advanceUntilIdle()
        insertAllData()
        advanceUntilIdle()
        val favoriteWithAirports = favoriteDao.getFavoriteWithAirports().first()
        advanceUntilIdle()
        assertEquals(2, favoriteWithAirports.size)
    }

    private suspend fun insertAllData() =
        favoriteEntitiesTestData.forEach { favoriteEntityTestData ->
            favoriteDao.insert(favoriteEntityTestData)
        }
}
