package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.AirportFtsEntity
import com.example.flightsearchapp.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AirportsFtsRepository {
    suspend fun upsertAirports(entities: List<AirportFtsEntity>)
    suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>)
    fun searchAirportsStream(query: String): Flow<List<String>>
}

class InDiskAirportsFtsRepository @Inject constructor(
    private val airportsFtsDataSource: AirportsFtsDataSource,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) : AirportsFtsRepository {
    override suspend fun upsertAirports(entities: List<AirportFtsEntity>) =
        withContext(defaultDispatcher) {
            airportsFtsDataSource.upsertAirports(entities = entities)
        }

    override suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>) =
        withContext(defaultDispatcher) {
            airportsFtsDataSource.deleteAndInsertAll(entities = entities)
        }

    override fun searchAirportsStream(query: String): Flow<List<String>> =
        airportsFtsDataSource.searchAirportsStream(query = query)
}
