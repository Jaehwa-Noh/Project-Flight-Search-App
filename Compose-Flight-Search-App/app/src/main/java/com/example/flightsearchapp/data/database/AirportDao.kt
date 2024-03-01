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
             WHERE id = :airportId
        """
    )
    fun getAirportNullByIdStream(airportId: Long): Flow<Airport?>

    @Query(
        """
        SELECT DISTINCT *
          FROM airport
         WHERE id != :airportId
         ORDER BY passengers DESC
    """
    )
    fun getAirportsStream(airportId: Long): Flow<List<Airport>>
}