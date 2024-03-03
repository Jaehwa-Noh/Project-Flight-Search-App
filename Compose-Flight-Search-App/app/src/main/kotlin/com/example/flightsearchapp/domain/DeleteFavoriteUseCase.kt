package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.FavoritesRepository
import com.example.flightsearchapp.di.DispatcherDefault
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