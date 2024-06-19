package com.example.flightsearchapp.core.data.repository

import com.example.flightsearchapp.core.data.di.DispatcherDefault
import com.example.flightsearchapp.core.model.Flight
import com.example.flightsearchapp.core.data.model.asFlight
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
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
    fun getAllFlightsStream(departureId: Long): Flow<List<com.example.flightsearchapp.core.model.Flight>> {
        return airportsRepository.getAirportNullByIdStream(airportId = departureId)
            .flatMapLatest { departureAirport ->
                if (departureAirport == null) {
                    flowOf(emptyList())
                } else {
                    combine(
                        airportsRepository.getAirportsStream(airportId = departureId),
                        favoritesRepository.getFavoritesStream(),
                    ) { arriveAirports, favorites ->
                        withContext(defaultDispatcher) {
                            val flightsModel = mutableListOf<com.example.flightsearchapp.core.model.Flight>()

                            val favoriteHashMap: HashMap<String, MutableSet<String>> = hashMapOf()
                            favorites.forEach { favorite ->
                                val arriveAirportsSet =
                                    favoriteHashMap.getOrDefault(
                                        favorite.departureCode,
                                        mutableSetOf(),
                                    )
                                arriveAirportsSet.add(favorite.destinationCode)
                                favoriteHashMap[favorite.departureCode] = arriveAirportsSet
                            }

                            arriveAirports.forEach { arriveAirport ->
                                flightsModel.add(
                                    com.example.flightsearchapp.core.model.Flight(
                                        id = "${departureAirport.id}_${arriveAirport.id}",
                                        departureIataCode = departureAirport.iataCode,
                                        departureName = departureAirport.name,
                                        arriveName = arriveAirport.name,
                                        arriveIataCode = arriveAirport.iataCode,
                                        isBookmarked =
                                        favoriteHashMap[departureAirport.iataCode]?.contains(
                                            arriveAirport.iataCode,
                                        ) ?: false,
                                    ),
                                )
                            }

                            flightsModel
                        }
                    }
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllFavoriteFlightsStream(): Flow<List<com.example.flightsearchapp.core.model.Flight>> =
        favoritesRepository.getFavoriteWithAirports().mapLatest { favoriteWithAirports ->
            favoriteWithAirports.map { favoriteWithAirport ->
                favoriteWithAirport.asFlight()
            }
        }
}
