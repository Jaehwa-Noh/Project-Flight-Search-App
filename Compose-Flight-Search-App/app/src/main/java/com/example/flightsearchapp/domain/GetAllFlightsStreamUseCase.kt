package com.example.flightsearchapp.domain

import com.example.flightsearchapp.di.DispatcherDefault
import com.example.flightsearchapp.ui.model.FlightModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllFlightsStreamUseCase @Inject constructor(
    private val getAirportsStreamUseCase: GetAirportsStreamUseCase,
    private val getAirportNullStreamUseCase: GetAirportNullStreamUseCase,
    private val getAllFavoritesStreamUseCase: GetAllFavoritesStreamUseCase,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(departureId: Long): Flow<List<FlightModel>> {
        return getAirportNullStreamUseCase(airportId = departureId).flatMapLatest { departureAirport ->
            if (departureAirport == null) {
                flowOf(emptyList())
            } else {
                combine(
                    getAirportsStreamUseCase(airportId = departureId),
                    getAllFavoritesStreamUseCase(),
                ) { arriveAirports, favorites ->
                    withContext(defaultDispatcher) {
                        val flightsModel = mutableListOf<FlightModel>()
                        val departureCodes = mutableListOf<String>()
                        val arriveCodes = mutableListOf<String>()

                        favorites.forEach { favorite ->
                            departureCodes.add(favorite.departureCode)
                            arriveCodes.add(favorite.destinationCode)
                        }

                        arriveAirports.forEach { arriveAirport ->
                            flightsModel.add(
                                FlightModel(
                                    id = "${departureAirport.id}_${arriveAirport.id}",
                                    departureIataCode = departureAirport.iataCode,
                                    departureName = departureAirport.name,
                                    arriveName = arriveAirport.name,
                                    arriveIataCode = arriveAirport.iataCode,
                                    isBookmarked =
                                    departureCodes
                                        .contains(departureAirport.iataCode) &&
                                            arriveCodes
                                                .contains(arriveAirport.iataCode),
                                )
                            )
                        }
                        return@withContext flightsModel
                    }
                }
            }
        }
    }
}