package com.example.flightsearchapp.data

import com.example.flightsearchapp.data.database.Airport
import com.example.flightsearchapp.data.database.AirportDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface AirportsDataSource {
    fun getSuggestionsStream(query: String): Flow<List<Airport>>
    fun getAirportNullStream(airportId: Long): Flow<Airport?>
    fun getAirportsStream(airportId: Long): Flow<List<Airport>>
}

@Singleton
class LocalAirportsDataSource @Inject constructor(
    private val airportDao: AirportDao,
) : AirportsDataSource {
    override fun getSuggestionsStream(query: String): Flow<List<Airport>> {
        return airportDao.getSuggestionsStream(query = query)
    }

    override fun getAirportNullStream(airportId: Long): Flow<Airport?> {
        return airportDao.getAirportNullStream(airportId = airportId)
    }

    override fun getAirportsStream(airportId: Long): Flow<List<Airport>> {
        return airportDao.getAirportsStream(airportId = airportId)
    }
}