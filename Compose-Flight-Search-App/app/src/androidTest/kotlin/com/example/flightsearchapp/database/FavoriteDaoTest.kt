package com.example.flightsearchapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.data.database.AppDatabase
import com.example.flightsearchapp.data.database.FavoriteDao
import com.example.flightsearchapp.testing.database.airportEntitiesTestData
import com.example.flightsearchapp.testing.database.favoriteEntitiesTestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FavoriteDaoTest {

    private lateinit var airportDao: AirportDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()

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
    fun whenGetAllFavorites_AllFavorites() = runTest {
        insertAllData()

        val allFavorites = favoriteDao.getAllFavoritesStream().first()

        assertEquals(favoriteEntitiesTestData, allFavorites)
    }

    @Test
    fun whenDeleteOneFavorite_ThreeRemain() = runTest {
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
    fun whenSearchFavorite_FavoriteWithAirports() = runTest {
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