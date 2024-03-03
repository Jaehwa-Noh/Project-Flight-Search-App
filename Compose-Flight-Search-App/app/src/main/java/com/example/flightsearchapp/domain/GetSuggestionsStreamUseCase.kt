package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.di.DispatcherDefault
import com.example.flightsearchapp.ui.model.SuggestionAirport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSuggestionsStreamUseCase @Inject constructor(
    private val airportsRepository: AirportsRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String): Flow<List<SuggestionAirport>> {
        return withContext(defaultDispatcher) {
            airportsRepository.getSuggestionsStream(query = query)
        }
    }
}