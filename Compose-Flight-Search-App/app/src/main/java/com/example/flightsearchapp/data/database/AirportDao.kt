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
             WHERE (iata_code LIKE :query) OR (name LIKE :query)
        """
    )
    fun getSuggestions(query: String): Flow<List<Airport>>
}