package com.example.flightsearchapp.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.flightsearchapp.core.database.model.AirportFtsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportFtsDao {
    @Upsert
    suspend fun upsertAirports(entities: List<AirportFtsEntity>)

    @Query(
        """
            DELETE FROM airportFts
        """,
    )
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAndInsertAll(entities: List<AirportFtsEntity>) {
        deleteAll()
        upsertAirports(entities = entities)
    }

    @Query(
        """
            SELECT iata_code
              FROM airportFts
             WHERE airportFts MATCH '*' || :query || '*'
        """,
    )
    fun searchAirportsStream(query: String): Flow<List<String>>
}
