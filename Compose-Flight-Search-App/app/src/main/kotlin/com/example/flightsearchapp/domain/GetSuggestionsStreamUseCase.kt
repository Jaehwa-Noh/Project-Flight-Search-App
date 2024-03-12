package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AirportsFtsRepository
import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.asSuggestionAirport
import com.example.flightsearchapp.di.DispatcherDefault
import com.example.flightsearchapp.ui.model.SuggestionAirport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSuggestionsStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
    private val airportsFtsRepository: AirportsFtsRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(query: String): Flow<List<SuggestionAirport>> =
        withContext(defaultDispatcher) {
            airportsFtsRepository.searchAirportsStream(query)
                .mapLatest { iataCodes ->
                    iataCodes.map { iataCode ->
                        airportsRepository.getAirportByCode(airportCode = iataCode)
                            .asSuggestionAirport()
                    }
                }.flowOn(defaultDispatcher)
                .catch {
                    emptyList<SuggestionAirport>()
                }
        }
}
