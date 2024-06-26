package com.example.flightsearchapp.core.data.datasource

import com.example.flightsearchapp.core.data.di.DispatcherIO
import com.example.flightsearchapp.core.database.dao.AirportDao
import com.example.flightsearchapp.core.database.model.AirportEntity
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
    suspend fun getAllAirportsEntities(): List<AirportEntity>
}

@Singleton
class LocalAirportsDataSource @Inject constructor(
    private val airportDao: AirportDao,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AirportsDataSource {
    override fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>> =
        airportDao.getAirportsByCodeOrNameStream(query = query)

    override fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?> =
        airportDao.getAirportNullByIdStream(airportId = airportId)

    override fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>> =
        airportDao.getAirportsStream(airportId = airportId)

    override suspend fun getAirportByCode(airportCode: String): AirportEntity =
        withContext(ioDispatcher) { airportDao.getAirportByCode(airportCode = airportCode) }

    override suspend fun getAllAirportsEntities(): List<AirportEntity> =
        airportDao.getAllAirportsEntities()
}
