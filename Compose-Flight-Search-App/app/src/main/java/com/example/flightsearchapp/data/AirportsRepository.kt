package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Airport
import com.example.flightsearchapp.ui.model.SuggestionAirportModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsRepository {
    suspend fun getSuggestionsStream(query: String): Flow<List<SuggestionAirportModel>>
    suspend fun getDepartureAirportStream(departureId: Long): Flow<Airport>
}

@Singleton
class InDiskAirportsRepository @Inject constructor(
    private val airportsDataSource: AirportsDataSource,
) : AirportsRepository {
    override suspend fun getSuggestionsStream(query: String): Flow<List<SuggestionAirportModel>> {
        return airportsDataSource.getSuggestionsStream(query = query).map {
            it.map { airport ->
                SuggestionAirportModel(
                    id = airport.id,
                    iataCode = airport.iataCode,
                    name = airport.name,
                )
            }
        }
    }

    override suspend fun getDepartureAirportStream(departureId: Long): Flow<Airport> {
        return airportsDataSource.getDepartureAirportStream(departureId = departureId)
    }
}


