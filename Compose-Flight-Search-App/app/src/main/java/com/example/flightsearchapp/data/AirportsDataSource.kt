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
    suspend fun getSuggestionsStream(query: String): Flow<List<Airport>>
    fun getDepartureAirportStream(departureId: Long): Flow<Airport>
    fun getArriveAirportsStream(departureId: Long): Flow<List<Airport>>
}

@Singleton
class LocalAirportsDataSource @Inject constructor(
    private val airportDao: AirportDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
) : AirportsDataSource {
    override suspend fun getSuggestionsStream(query: String): Flow<List<Airport>> {
        return withContext(ioDispatcher) {
            airportDao.getSuggestionsStream(query = query)
        }
    }

    override fun getDepartureAirportStream(departureId: Long): Flow<Airport> {
        return airportDao.getDepartureAirportStream(departureId = departureId)
    }

    override fun getArriveAirportsStream(departureId: Long): Flow<List<Airport>> {
        return airportDao.getArriveAirportsStream(departureId = departureId)
    }
}