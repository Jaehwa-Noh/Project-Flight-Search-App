package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AllFlightsRepository
import com.example.flightsearchapp.ui.model.Flight
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteFlightsStreamUseCase @Inject constructor(
    private val getAllFlightsRepository: AllFlightsRepository,
) {
    operator fun invoke(): Flow<List<Flight>> {
        return getAllFlightsRepository.getAllFavoriteFlightsStream()
    }
}
