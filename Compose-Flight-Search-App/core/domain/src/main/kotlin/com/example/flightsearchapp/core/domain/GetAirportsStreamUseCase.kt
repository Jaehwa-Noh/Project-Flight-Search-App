package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.repository.AirportsRepository
import com.example.flightsearchapp.core.database.model.AirportEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAirportsStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
) {
    operator fun invoke(airportId: Long): Flow<List<AirportEntity>> {
        return airportsRepository.getAirportsStream(airportId = airportId)
    }
}
