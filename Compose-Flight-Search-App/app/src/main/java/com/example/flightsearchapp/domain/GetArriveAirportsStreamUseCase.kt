package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.Airport
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArriveAirportsStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
) {
    operator fun invoke(departureId: Long): Flow<List<Airport>> {
        return airportsRepository.getArriveAirportsStream(departureId = departureId)
    }
}