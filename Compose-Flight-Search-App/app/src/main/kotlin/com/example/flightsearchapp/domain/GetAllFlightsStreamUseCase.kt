package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AllFlightsRepository
import com.example.flightsearchapp.ui.model.Flight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFlightsStreamUseCase @Inject constructor(
    private val allFlightsRepository: AllFlightsRepository,
) {
    operator fun invoke(departureId: Long): Flow<List<Flight>> {
        return allFlightsRepository.getAllFlightsStream(departureId = departureId)
    }
}