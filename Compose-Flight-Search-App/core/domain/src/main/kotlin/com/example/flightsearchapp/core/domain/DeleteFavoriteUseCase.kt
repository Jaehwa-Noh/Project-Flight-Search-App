package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.di.DispatcherDefault
import com.example.flightsearchapp.core.data.repository.FavoritesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(departureCode: String, arriveCode: String) {
        withContext(defaultDispatcher) {
            favoritesRepository.deleteFavorite(
                departureCode = departureCode,
                arriveCode = arriveCode,
            )
        }
    }
}
