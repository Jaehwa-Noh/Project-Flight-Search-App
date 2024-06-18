package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.model.Flight
import com.example.flightsearchapp.core.data.repository.AllFlightsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteFlightsStreamUseCase @Inject constructor(
    private val getAllFlightsRepository: AllFlightsRepository,
) {
    operator fun invoke(): Flow<List<Flight>> {
        return getAllFlightsRepository.getAllFavoriteFlightsStream()
    }
}
