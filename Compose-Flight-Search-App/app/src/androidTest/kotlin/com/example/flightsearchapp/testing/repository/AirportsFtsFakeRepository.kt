package com.example.flightsearchapp.testing.repository

import com.example.flightsearchapp.data.AirportsFtsRepository
import com.example.flightsearchapp.data.database.AirportFtsEntity
import com.example.flightsearchapp.di.DispatcherIO
import com.example.flightsearchapp.testing.model.database.airportFtsEntitiesTestData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AirportsFtsFakeRepository @Inject constructor(
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AirportsFtsRepository {
    override suspend fun upsertAirports(entities: List<AirportFtsEntity>) {
    }

    override suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>) {
    }

    override fun searchAirportsStream(query: String): Flow<List<String>> = flow {
        val filtered = airportFtsEntitiesTestData.filter {
            it.name == query || it.iataCode == query
        }.map {
            it.iataCode
        }
        if (filtered.isEmpty()) {
            emit(
                emptyList(),
            )
        } else {
            emit(
                filtered,
            )
        }
    }.flowOn(ioDispatcher)
}
