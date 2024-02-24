package com.example.flightsearchapp.data

import com.example.flightsearchapp.ui.model.SearchedAirport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsRepository {
    suspend fun getSuggestionsStream(query: String): Flow<List<SearchedAirport>>
}

@Singleton
class InDiskAirportsRepository @Inject constructor(
    private val airportsDataSource: AirportsDataSource,
) : AirportsRepository {
    override suspend fun getSuggestionsStream(query: String): Flow<List<SearchedAirport>> {
        return airportsDataSource.getSuggestionsStream(query = query).map {
            it.map { airport ->
                SearchedAirport(
                    id = airport.id,
                    iataCode = airport.iataCode,
                    name = airport.name,
                )
            }
        }
    }
}


