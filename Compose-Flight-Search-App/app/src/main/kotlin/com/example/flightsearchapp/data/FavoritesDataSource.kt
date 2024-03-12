package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.FavoriteEntity
import com.example.flightsearchapp.data.database.FavoriteDao
import com.example.flightsearchapp.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface FavoritesDataSource {
    fun getFavoritesStream(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(departureCode: String, arriveCode: String)
}

@Singleton
class LocalFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : FavoritesDataSource {
    override fun getFavoritesStream(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoritesStream()
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
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