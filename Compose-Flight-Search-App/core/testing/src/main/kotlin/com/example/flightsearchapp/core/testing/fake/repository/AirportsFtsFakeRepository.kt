package com.example.flightsearchapp.core.testing.fake.repository

import com.example.flightsearchapp.core.data.di.DispatcherIO
import com.example.flightsearchapp.core.data.repository.AirportsFtsRepository
import com.example.flightsearchapp.core.database.model.AirportFtsEntity
import com.example.flightsearchapp.core.testing.fake.data.database.airportFtsEntitiesTestData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

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
