package com.example.flightsearchapp.core.data.datasource

import com.example.flightsearchapp.core.data.di.DispatcherIO
import com.example.flightsearchapp.core.database.dao.AirportFtsDao
import com.example.flightsearchapp.core.database.model.AirportFtsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

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
