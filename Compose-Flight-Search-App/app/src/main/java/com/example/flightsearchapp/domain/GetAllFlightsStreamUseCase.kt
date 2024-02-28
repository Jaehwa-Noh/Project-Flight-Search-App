package com.example.flightsearchapp.domain

import com.example.flightsearchapp.ui.model.FlightModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllFlightsStreamUseCase @Inject constructor(
    private val getArriveAirportsStreamUseCase: GetArriveAirportsStreamUseCase,
    private val getDepartureAirportStreamUseCase: GetDepartureAirportStreamUseCase,
) {
    operator fun invoke(departureId: Long): Flow<List<FlightModel>> {
        return getDepartureAirportStreamUseCase(departureId = departureId)
            .combine(
                getArriveAirportsStreamUseCase(departureId = departureId),
            ) { departureAirport, arriveAirports ->
                val flightsModel = mutableListOf<FlightModel>()

                arriveAirports.forEach { arriveAirport ->
                    flightsModel.add(
                        FlightModel(
                            id = "${departureAirport.id}_${arriveAirport.id}",
                            departureId = departureAirport.id,
                            departureIataCode = departureAirport.iataCode,
                            departureName = departureAirport.name,
                            arriveId = arriveAirport.id,
                            arriveName = arriveAirport.name,
                            arriveIataCode = arriveAirport.iataCode,
                        )
                    )
                }
                return@combine flightsModel
            }
    }
}