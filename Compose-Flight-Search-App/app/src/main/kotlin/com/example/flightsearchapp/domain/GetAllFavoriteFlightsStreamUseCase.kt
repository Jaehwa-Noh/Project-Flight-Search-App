package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AllFlightsRepository
import com.example.flightsearchapp.ui.model.Flight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteFlightsStreamUseCase @Inject constructor(
    private val getAllFlightsRepository: AllFlightsRepository,
) {
    operator fun invoke(): Flow<List<Flight>> {
        return getAllFlightsRepository.getAllFavoriteFlightsStream()
    }
}