package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.di.DispatcherDefault
import com.example.flightsearchapp.core.data.model.asSuggestionAirport
import com.example.flightsearchapp.core.data.repository.AirportsFtsRepository
import com.example.flightsearchapp.core.data.repository.AirportsRepository
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
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(query: String): Flow<List<com.example.flightsearchapp.core.model.SuggestionAirport>> =
        withContext(defaultDispatcher) {
            airportsFtsRepository.searchAirportsStream(query)
                .mapLatest { iataCodes ->
                    iataCodes.map { iataCode ->
                        airportsRepository.getAirportByCode(airportCode = iataCode)
                            .asSuggestionAirport()
                    }
                }.flowOn(defaultDispatcher)
                .catch {
                    emptyList<com.example.flightsearchapp.core.model.SuggestionAirport>()
                }
        }
}
