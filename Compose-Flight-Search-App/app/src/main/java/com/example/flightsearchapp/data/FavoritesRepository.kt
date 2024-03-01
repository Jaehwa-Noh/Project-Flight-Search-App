package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoritesRepository {
    fun getFavoritesStream(): Flow<List<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(departureCode: String, arriveCode: String)
}

class InDiskFavoritesRepository @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
) : FavoritesRepository {
    override fun getFavoritesStream(): Flow<List<Favorite>> {
        return favoritesDataSource.getFavoritesStream()
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        favoritesDataSource.insertFavorite(favorite = favorite)
    }

    override suspend fun deleteFavorite(departureCode: String, arriveCode: String) {
        favoritesDataSource.deleteFavorite(
            departureCode = departureCode,
            arriveCode = arriveCode,
        )
    }
}