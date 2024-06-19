package com.example.flightsearchapp.core.testing.fake.datasource

import com.example.flightsearchapp.core.data.datasource.AirportsDataSource
import com.example.flightsearchapp.core.database.model.AirportEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

/**
 * FakeDataSource for [AirportsDataSource].
 */
class AirportsFakeDataSource : AirportsDataSource {
    private val _airportEntityStream: MutableSharedFlow<List<AirportEntity>> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val airportEntityStream: Flow<List<AirportEntity>> = _airportEntityStream.asSharedFlow()
    private val airports = mutableListOf<AirportEntity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>> =
        airportEntityStream.mapLatest { airportsEntities ->
            airportsEntities.filter { airportEntity ->
                airportEntity.name == query || airportEntity.iataCode == query
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?> =
        airportEntityStream.mapLatest { airportsEntities ->

            val filteredAirport = airportsEntities.filter { airportEntity ->
                airportEntity.id == airportId
            }
            if (filteredAirport.isEmpty()) {
                return@mapLatest null
            }

            filteredAirport.first()
        }

    override fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>> =
        airportEntityStream.map { airportsEntities ->
            airportsEntities.filter { airportEntity ->
                airportEntity.id == airportId
            }
        }

    override suspend fun getAirportByCode(airportCode: String): AirportEntity =
        airportEntityStream.map { airportsEntities ->
            val filteredAirportsEntities = airportsEntities.filter { airportEntity ->
                airportEntity.iataCode == airportCode
            }
            filteredAirportsEntities
        }.first().first()

    override suspend fun getAllAirportsEntities(): List<AirportEntity> = airportEntityStream.first()

    fun insertAirportsEntities(airportsEntities: List<AirportEntity>) {
        val distinctAirports = airports.toMutableSet()
        distinctAirports.addAll(airportsEntities)

        _airportEntityStream.tryEmit(distinctAirports.toMutableList())
    }
}
