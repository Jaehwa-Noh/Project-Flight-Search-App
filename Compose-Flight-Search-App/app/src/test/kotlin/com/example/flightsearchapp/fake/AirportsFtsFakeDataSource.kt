package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.AirportsFtsDataSource
import com.example.flightsearchapp.data.database.AirportFtsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapLatest

/**
 * FakeDataSource for [AirportsFtsDataSource]
 */
class AirportsFtsFakeDataSource : AirportsFtsDataSource {
    private val _airportFtsEntitiesStream: MutableSharedFlow<List<AirportFtsEntity>> =
        MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    val airportFtsEntitiesStream: Flow<List<AirportFtsEntity>> =
        _airportFtsEntitiesStream.asSharedFlow()
    private val airportsFts = mutableListOf<AirportFtsEntity>()

    override suspend fun upsertAirports(entities: List<AirportFtsEntity>) {
        entities.forEach { newAirportFts ->
            if (airportsFts.contains(newAirportFts)) return@forEach
            airportsFts.add(newAirportFts)
        }

        _airportFtsEntitiesStream.tryEmit(airportsFts)
    }

    override suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>) {
        airportsFts.clear()
        airportsFts.addAll(entities)

        _airportFtsEntitiesStream.tryEmit(airportsFts)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchAirportsStream(query: String): Flow<List<String>> {
        return airportFtsEntitiesStream.mapLatest { airportsFtsEntities ->
            airportsFtsEntities
                .filter { airportFts ->
                    airportFts.iataCode == query || airportFts.name == query
                }.map {
                    it.iataCode
                }
        }
    }
}
