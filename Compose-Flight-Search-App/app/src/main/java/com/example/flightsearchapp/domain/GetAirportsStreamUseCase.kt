package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.Airport
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAirportsStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
) {
    operator fun invoke(airportId: Long): Flow<List<Airport>> {
        return airportsRepository.getAirportsStream(airportId = airportId)
    }
}