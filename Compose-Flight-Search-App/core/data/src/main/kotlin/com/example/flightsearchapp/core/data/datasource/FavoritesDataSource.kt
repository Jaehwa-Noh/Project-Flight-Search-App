package com.example.flightsearchapp.core.data.datasource

import com.example.flightsearchapp.core.data.di.DispatcherIO
import com.example.flightsearchapp.core.database.dao.FavoriteDao
import com.example.flightsearchapp.core.database.model.FavoriteEntity
import com.example.flightsearchapp.core.database.model.FavoriteWithAirports
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface FavoritesDataSource {
    fun getFavoritesStream(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(departureCode: String, arriveCode: String)
    fun getFavoriteWithAirports(): Flow<List<FavoriteWithAirports>>
}

@Singleton
class LocalFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : FavoritesDataSource {
    override fun getFavoritesStream(): Flow<List<FavoriteEntity>> =
        favoriteDao.getAllFavoritesStream()

    override suspend fun insertFavorite(favorite: FavoriteEntity) = withContext(ioDispatcher) {
        favoriteDao.insert(favorite = favorite)
    }

    override suspend fun deleteFavorite(departureCode: String, arriveCode: String) =
        withContext(ioDispatcher) {
            favoriteDao.delete(
                departureCode = departureCode,
                arriveCode = arriveCode,
            )
        }

    override fun getFavoriteWithAirports(): Flow<List<FavoriteWithAirports>> =
        favoriteDao.getFavoriteWithAirports()
}
