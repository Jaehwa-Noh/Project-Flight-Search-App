package com.example.flightsearchapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportFtsDao {
    @Upsert
    suspend fun upsertAirports(entities: List<AirportFtsEntity>)

    @Query(
        """
        SELECT iata_code
          FROM airportFts
         WHERE airportFts MATCH '*' || :query || '*'
    """
    )
    fun searchAirportsStream(query: String): Flow<List<String>>
}


