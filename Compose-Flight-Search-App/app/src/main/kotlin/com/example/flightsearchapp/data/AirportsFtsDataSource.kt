package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.AirportFtsDao
import com.example.flightsearchapp.data.database.AirportFtsEntity
import com.example.flightsearchapp.di.DispatcherIO
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface AirportsFtsDataSource {
    suspend fun upsertAirports(entities: List<AirportFtsEntity>)
    suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>)
    fun searchAirportsStream(query: String): Flow<List<String>>
}

@Singleton
class LocalAirportsFtsDataSource @Inject constructor(
    private val airportsFtsDao: AirportFtsDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AirportsFtsDataSource {
    override suspend fun upsertAirports(entities: List<AirportFtsEntity>) =
        withContext(ioDispatcher) {
            airportsFtsDao.upsertAirports(entities = entities)
        }

    override suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>) =
        withContext(ioDispatcher) {
            airportsFtsDao.deleteAndInsertAll(entities = entities)
        }

    override fun searchAirportsStream(query: String): Flow<List<String>> =
        airportsFtsDao.searchAirportsStream(query = query)
}
