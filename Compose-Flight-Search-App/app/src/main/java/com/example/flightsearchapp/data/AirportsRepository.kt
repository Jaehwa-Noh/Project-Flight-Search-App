package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Airport
import com.example.flightsearchapp.di.DispatcherDefault
import com.example.flightsearchapp.ui.model.SuggestionAirportModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsRepository {
    suspend fun getSuggestionsStream(query: String): Flow<List<SuggestionAirportModel>>
    fun getAirportsByCodeOrNameStream(query: String): Flow<List<Airport>>
    fun getAirportNullByIdStream(airportId: Long): Flow<Airport?>
    fun getAirportsStream(airportId: Long): Flow<List<Airport>>
    suspend fun getAirportByCode(airportCode: String): Airport
}

@Singleton
class InDiskAirportsRepository @Inject constructor(
    private val airportsDataSource: AirportsDataSource,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) : AirportsRepository {
    override suspend fun getSuggestionsStream(query: String): Flow<List<SuggestionAirportModel>> {
        return getAirportsByCodeOrNameStream(query = query).map {
            withContext(defaultDispatcher) {
                it.map { airport ->
                    SuggestionAirportModel(
                        id = airport.id,
                        iataCode = airport.iataCode,
                        name = airport.name,
                    )
                }
            }
        }
    }

    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<Airport>> {
        return airportsDataSource.getAirportsByCodeOrNameStream(query = query)
    }

    override fun getAirportNullByIdStream(airportId: Long): Flow<Airport?> {
        return airportsDataSource.getAirportNullByIdStream(airportId = airportId)
    }

    override fun getAirportsStream(airportId: Long): Flow<List<Airport>> {
        return airportsDataSource.getAirportsStream(airportId = airportId)
    }

    override suspend fun getAirportByCode(airportCode: String): Airport {
        return airportsDataSource.getAirportByCode(airportCode = airportCode)
    }
}


