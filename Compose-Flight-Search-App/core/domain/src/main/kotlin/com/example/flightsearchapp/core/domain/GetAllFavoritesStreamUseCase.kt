package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.repository.FavoritesRepository
import com.example.flightsearchapp.core.database.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesStreamUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    operator fun invoke(): Flow<List<FavoriteEntity>> {
        return favoritesRepository.getFavoritesStream()
    }
}
