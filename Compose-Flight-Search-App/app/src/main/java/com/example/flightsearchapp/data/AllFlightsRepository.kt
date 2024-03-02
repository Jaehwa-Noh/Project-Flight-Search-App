package com.example.flightsearchapp.data

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
import javax.inject.Singleton

@Singleton
class AllFlightsRepository @Inject constructor(
    private val airportsRepository: AirportsRepository,
    private val favoritesRepository: FavoritesRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllFlightsStream(departureId: Long): Flow<List<FlightModel>> {
        return airportsRepository.getAirportNullByIdStream(airportId = departureId)
            .flatMapLatest { departureAirport ->
                if (departureAirport == null) {
                    flowOf(emptyList())
                } else {
                    combine(
                        airportsRepository.getAirportsStream(airportId = departureId),
                        favoritesRepository.getFavoritesStream()
                    ) { arriveAirports, favorites ->
                        withContext(defaultDispatcher) {
                            val flightsModel = mutableListOf<FlightModel>()

                            val favoriteHashMap: HashMap<String, MutableSet<String>> = hashMapOf()
                            favorites.forEach { favorite ->
                                val arriveAirportsSet =
                                    favoriteHashMap.getOrDefault(
                                        favorite.departureCode,
                                        mutableSetOf()
                                    )
                                arriveAirportsSet.add(favorite.destinationCode)
                                favoriteHashMap[favorite.departureCode] = arriveAirportsSet
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
                                        favoriteHashMap[departureAirport.iataCode]?.contains(
                                            arriveAirport.iataCode
                                        ) ?: false
                                    )
                                )
                            }

                            flightsModel
                        }
                    }
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllFavoriteFlightsStream(): Flow<List<FlightModel>> {
        return favoritesRepository.getFavoritesStream().flatMapLatest { favorites ->
            if (favorites.isEmpty()) return@flatMapLatest flowOf(emptyList())

            val flightsStream = favorites.map { favorite ->
                val departureAirport =
                    airportsRepository.getAirportByCode(airportCode = favorite.departureCode)

                val arriveAirport =
                    airportsRepository.getAirportByCode(airportCode = favorite.destinationCode)

                FlightModel(
                    id = "${departureAirport.id}_${arriveAirport.id}",
                    departureIataCode = departureAirport.iataCode,
                    departureName = departureAirport.name,
                    arriveIataCode = arriveAirport.iataCode,
                    arriveName = arriveAirport.name,
                    isBookmarked =
                    favorite.departureCode == departureAirport.iataCode
                            && favorite.destinationCode == arriveAirport.iataCode
                )
            }

            flowOf(flightsStream)
        }
    }
}
