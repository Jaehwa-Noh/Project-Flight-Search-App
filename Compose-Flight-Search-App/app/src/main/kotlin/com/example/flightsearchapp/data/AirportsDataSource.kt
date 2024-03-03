package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.AirportEntity
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsDataSource {
    fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>>
    fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?>
    fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>>
    suspend fun getAirportByCode(airportCode: String): AirportEntity
}

@Singleton
class LocalAirportsDataSource @Inject constructor(
    private val airportDao: AirportDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AirportsDataSource {
    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>> {
        return airportDao.getAirportsByCodeOrNameStream(query = query)
    }

    override fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?> {
        return airportDao.getAirportNullByIdStream(airportId = airportId)
    }

    override fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>> {
        return airportDao.getAirportsStream(airportId = airportId)
    }

    override suspend fun getAirportByCode(airportCode: String): AirportEntity {
        return withContext(ioDispatcher) { airportDao.getAirportByCode(airportCode = airportCode) }
    }
}