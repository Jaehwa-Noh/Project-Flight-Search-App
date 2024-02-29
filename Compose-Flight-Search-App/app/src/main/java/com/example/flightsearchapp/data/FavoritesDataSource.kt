package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Favorite
import com.example.flightsearchapp.data.database.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface FavoritesDataSource {
    fun getFavoritesStream(): Flow<List<Favorite>>
    fun insertFavorite(favorite: Favorite)
    fun deleteFavorite(favoriteId: Long)
}

@Singleton
class LocalFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoritesDataSource {
    override fun getFavoritesStream(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavoritesStream()
    }

    override fun insertFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite = favorite)
    }

    override fun deleteFavorite(favoriteId: Long) {
        favoriteDao.delete(favoriteId = favoriteId)
    }
}