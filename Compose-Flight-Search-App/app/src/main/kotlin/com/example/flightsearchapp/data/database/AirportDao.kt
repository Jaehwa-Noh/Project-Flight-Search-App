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
    fun getAirportsByCodeOrNameStream(query: String): Flow<List<AirportEntity>>

    @Query(
        """
            SELECT *
              FROM airport
             WHERE id = :airportId
        """
    )
    fun getAirportNullByIdStream(airportId: Long): Flow<AirportEntity?>

    @Query(
        """
            SELECT *
              FROM airport
             WHERE iata_code = :airportCode
        """
    )
    suspend fun getAirportByCode(airportCode: String): AirportEntity

    @Query(
        """
        SELECT DISTINCT *
          FROM airport
         WHERE id != :airportId
         ORDER BY passengers DESC
    """
    )
    fun getAirportsStream(airportId: Long): Flow<List<AirportEntity>>

    @Query(
        """
            SELECT DISTINCT *
              FROM airport
        """
    )
    fun getAllAirportsStream(): Flow<List<AirportEntity>>
}
