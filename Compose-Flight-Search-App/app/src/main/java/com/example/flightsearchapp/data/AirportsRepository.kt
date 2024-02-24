package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Airport
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsRepository {
    suspend fun getSuggestionsStream(query: String): Flow<List<Airport>>
}

@Singleton
class InDiskAirportsRepository @Inject constructor(
    private val airportsDataSource: AirportsDataSource,
) : AirportsRepository {
    override suspend fun getSuggestionsStream(query: String): Flow<List<Airport>> {
        return airportsDataSource.getSuggestionsStream(query = query)
    }
}


