package com.example.flightsearchapp.core.data.repository

import com.example.flightsearchapp.core.data.datasource.AirportsDataSource
import com.example.flightsearchapp.core.data.di.DispatcherDefault
import com.example.flightsearchapp.core.database.model.AirportEntity
import com.example.flightsearchapp.core.model.SuggestionAirport
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface AirportsRepository {
    suspend fun getSuggestionsStream(query: String): Flow<List<com.example.flightsearchapp.core.model.SuggestionAirport>>
    fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>>
    fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?>
    fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>>
    suspend fun getAirportByCode(airportCode: String): AirportEntity

    suspend fun getAllAirportsEntities(): List<AirportEntity>
}

@Singleton
class InDiskAirportsRepository @Inject constructor(
    private val airportsDataSource: AirportsDataSource,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) : AirportsRepository {
    override suspend fun getSuggestionsStream(query: String): Flow<List<com.example.flightsearchapp.core.model.SuggestionAirport>> {
        return getAirportsByCodeOrNameStream(query = query).map {
            withContext(defaultDispatcher) {
                it.map { airport ->
                    com.example.flightsearchapp.core.model.SuggestionAirport(
                        id = airport.id,
                        iataCode = airport.iataCode,
                        name = airport.name,
                        passengers = airport.passengers,
                    )
                }
            }
        }
    }

    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>> =
        airportsDataSource.getAirportsByCodeOrNameStream(query = query)

    override fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?> =
        airportsDataSource.getAirportNullByIdStream(airportId = airportId)

    override fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>> =
        airportsDataSource.getAirportsStream(airportId = airportId)

    override suspend fun getAirportByCode(airportCode: String): AirportEntity =
        airportsDataSource.getAirportByCode(airportCode = airportCode)

    override suspend fun getAllAirportsEntities(): List<AirportEntity> =
        airportsDataSource.getAllAirportsEntities()
}
