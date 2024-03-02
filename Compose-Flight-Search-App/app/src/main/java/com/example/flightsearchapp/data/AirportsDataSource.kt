package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Airport
import com.example.flightsearchapp.data.database.AirportDao
import com.example.flightsearchapp.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsDataSource {
    fun getAirportsByCodeOrNameStream(query: String): Flow<List<Airport>>
    fun getAirportNullByIdStream(airportId: Long): Flow<Airport?>
    fun getAirportsStream(airportId: Long): Flow<List<Airport>>
    suspend fun getAirportByCode(airportCode: String): Airport
}

@Singleton
class LocalAirportsDataSource @Inject constructor(
    private val airportDao: AirportDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AirportsDataSource {
    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<Airport>> {
        return airportDao.getAirportsByCodeOrNameStream(query = query)
    }

    override fun getAirportNullByIdStream(airportId: Long): Flow<Airport?> {
        return airportDao.getAirportNullByIdStream(airportId = airportId)
    }

    override fun getAirportsStream(airportId: Long): Flow<List<Airport>> {
        return airportDao.getAirportsStream(airportId = airportId)
    }

    override suspend fun getAirportByCode(airportCode: String): Airport {
        return withContext(ioDispatcher) { airportDao.getAirportByCode(airportCode = airportCode) }
    }
}