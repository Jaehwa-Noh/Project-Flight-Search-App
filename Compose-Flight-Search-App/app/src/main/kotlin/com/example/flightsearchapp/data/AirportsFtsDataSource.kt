package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.AirportFtsDao
import com.example.flightsearchapp.data.database.AirportFtsEntity
import com.example.flightsearchapp.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsFtsDataSource {
    suspend fun upsertAirports(entities: List<AirportFtsEntity>)
    fun searchAirports(query: String): Flow<List<String>>
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


    override fun searchAirports(query: String): Flow<List<String>> =
        airportsFtsDao.searchAirports(query = query)

}

