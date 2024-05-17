package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.AirportEntity
import com.example.flightsearchapp.di.DispatcherDefault
import com.example.flightsearchapp.ui.model.SuggestionAirport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsRepository {
    suspend fun getSuggestionsStream(query: String): Flow<List<SuggestionAirport>>
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
    override suspend fun getSuggestionsStream(query: String): Flow<List<SuggestionAirport>> {
        return getAirportsByCodeOrNameStream(query = query).map {
            withContext(defaultDispatcher) {
                it.map { airport ->
                    SuggestionAirport(
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
