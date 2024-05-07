package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.AirportsDataSource
import com.example.flightsearchapp.data.database.AirportEntity
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
    private val _airportEntityFlow: MutableSharedFlow<List<AirportEntity>> = MutableSharedFlow(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val airportEntityFlow: Flow<List<AirportEntity>> = _airportEntityFlow.asSharedFlow()
    private val airports = mutableListOf<AirportEntity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>> =
        airportEntityFlow.mapLatest { airportsEntities ->
            airportsEntities.filter { airportEntity ->
                airportEntity.name == query || airportEntity.iataCode == query
            }
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?> =
        airportEntityFlow.mapLatest { airportsEntities ->

            val filteredAirport = airportsEntities.filter { airportEntity ->
                airportEntity.id == airportId
            }
            if (filteredAirport.isEmpty()) {
                return@mapLatest null
            }

            filteredAirport.first()
        }


    override fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>> =
        airportEntityFlow.map { airportsEntities ->
            airportsEntities.filter { airportEntity ->
                airportEntity.id == airportId
            }
        }


    override suspend fun getAirportByCode(airportCode: String): AirportEntity =
        airportEntityFlow.map { airportsEntities ->
            val filteredAirportsEntities = airportsEntities.filter { airportEntity ->
                airportEntity.iataCode == airportCode
            }
            filteredAirportsEntities
        }.first().first()


    override suspend fun getAllAirportsEntities(): List<AirportEntity> =
        airportEntityFlow.first()

    fun insertAirportsEntities(airportsEntities: List<AirportEntity>) {
        val distinctAirports = airports.toMutableSet()
        distinctAirports.addAll(airportsEntities)

        _airportEntityFlow.tryEmit(distinctAirports.toMutableList())
    }
}
