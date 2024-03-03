package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.FavoritesRepository
import com.example.flightsearchapp.data.database.FavoriteEntity
import com.example.flightsearchapp.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class InsertFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(departureCode: String, arriveCode: String) {
        withContext(defaultDispatcher) {
            favoritesRepository.insertFavorite(
                FavoriteEntity(
                    id = 0,
                    departureCode = departureCode,
                    destinationCode = arriveCode,
                )
            )
        }
    }
}