package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.Airport
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAirportNullStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
) {
    operator fun invoke(airportId: Long): Flow<Airport?> {
        return airportsRepository.getAirportNullStream(airportId)
    }
}