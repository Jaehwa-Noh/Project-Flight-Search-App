package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoritesRepository {
    fun getFavoritesStream(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(departureCode: String, arriveCode: String)
}

class InDiskFavoritesRepository @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
) : FavoritesRepository {
    override fun getFavoritesStream(): Flow<List<FavoriteEntity>> =
        favoritesDataSource.getFavoritesStream()


    override suspend fun insertFavorite(favorite: FavoriteEntity) =
        favoritesDataSource.insertFavorite(favorite = favorite)


    override suspend fun deleteFavorite(departureCode: String, arriveCode: String) =
        favoritesDataSource.deleteFavorite(
            departureCode = departureCode,
            arriveCode = arriveCode,
        )
    }
}
