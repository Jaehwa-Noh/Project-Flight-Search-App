package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.repository.AllFlightsRepository
import com.example.flightsearchapp.core.model.Flight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFlightsStreamUseCase @Inject constructor(
    private val allFlightsRepository: AllFlightsRepository,
) {
    operator fun invoke(departureId: Long): Flow<List<Flight>> {
        return allFlightsRepository.getAllFlightsStream(departureId = departureId)
    }
}
