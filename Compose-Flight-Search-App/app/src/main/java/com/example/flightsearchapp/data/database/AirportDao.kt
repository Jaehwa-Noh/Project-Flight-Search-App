package com.example.flightsearchapp.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query(
        """
            SELECT *
              FROM airport 
             WHERE (iata_code LIKE :query) OR (name LIKE '%' || :query || '%')
             ORDER BY passengers DESC
        """
    )
    fun getSuggestionsStream(query: String): Flow<List<Airport>>

    @Query(
        """
            SELECT *
              FROM airport
             WHERE id = :departureId
        """
    )
    fun getDepartureAirportStream(departureId: Long): Flow<Airport>

    @Query(
        """
        SELECT DISTINCT *
          FROM airport
         WHERE id != :departureId
         ORDER BY passengers DESC
    """
    )
    fun getArriveAirportsStream(departureId: Long): Flow<List<Airport>>
}