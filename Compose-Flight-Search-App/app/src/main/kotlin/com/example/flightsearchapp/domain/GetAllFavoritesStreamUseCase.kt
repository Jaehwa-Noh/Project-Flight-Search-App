package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.FavoritesRepository
import com.example.flightsearchapp.data.database.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesStreamUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    operator fun invoke(): Flow<List<FavoriteEntity>> {
        return favoritesRepository.getFavoritesStream()
    }
}