package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.Airport
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDepartureAirportStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
) {
    operator fun invoke(departureId: Long): Flow<Airport?> {
        return airportsRepository.getDepartureAirportStream(departureId)
    }
}