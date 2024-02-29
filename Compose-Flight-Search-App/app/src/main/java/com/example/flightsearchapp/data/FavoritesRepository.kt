package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoritesRepository {
    fun getFavoritesStream(): Flow<List<Favorite>>
    fun insertFavorite(favorite: Favorite)
    fun deleteFavorite(favoriteId: Long)
}

class InDiskFavoritesRepository @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
) : FavoritesRepository {
    override fun getFavoritesStream(): Flow<List<Favorite>> {
        return favoritesDataSource.getFavoritesStream()
    }

    override fun insertFavorite(favorite: Favorite) {
        favoritesDataSource.insertFavorite(favorite = favorite)
    }

    override fun deleteFavorite(favoriteId: Long) {
        favoritesDataSource.deleteFavorite(favoriteId = favoriteId)
    }
}