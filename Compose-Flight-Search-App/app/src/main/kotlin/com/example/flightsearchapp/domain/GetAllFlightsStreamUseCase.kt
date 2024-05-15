package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AllFlightsRepository
import com.example.flightsearchapp.ui.model.Flight
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllFlightsStreamUseCase @Inject constructor(
    private val allFlightsRepository: AllFlightsRepository,
) {
    operator fun invoke(departureId: Long): Flow<List<Flight>> {
        return allFlightsRepository.getAllFlightsStream(departureId = departureId)
    }
}
