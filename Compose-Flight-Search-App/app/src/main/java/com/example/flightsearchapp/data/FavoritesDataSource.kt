package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Favorite
import com.example.flightsearchapp.data.database.FavoriteDao
import com.example.flightsearchapp.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface FavoritesDataSource {
    fun getFavoritesStream(): Flow<List<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(departureCode: String, arriveCode: String)
}

@Singleton
class LocalFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : FavoritesDataSource {
    override fun getFavoritesStream(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavoritesStream()
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        withContext(ioDispatcher) {
            favoriteDao.insert(favorite = favorite)
        }
    }

    override suspend fun deleteFavorite(departureCode: String, arriveCode: String) {
        withContext(ioDispatcher) {
            favoriteDao.delete(
                departureCode = departureCode,
                arriveCode = arriveCode,
            )
        }
    }
}