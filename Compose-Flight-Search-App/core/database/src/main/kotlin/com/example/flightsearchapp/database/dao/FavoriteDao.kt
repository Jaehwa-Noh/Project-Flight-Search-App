package com.example.flightsearchapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.flightsearchapp.database.model.FavoriteEntity
import com.example.flightsearchapp.database.model.FavoriteWithAirports
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query(
        """
            SELECT *
              FROM favorite
        """,
    )
    fun getAllFavoritesStream(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query(
        """
            DELETE FROM favorite
             WHERE departure_code = :departureCode AND destination_code = :arriveCode
        """,
    )
    suspend fun delete(departureCode: String, arriveCode: String)

    @Transaction
    @Query(
        """
            SELECT *
              FROM favorite
        """,
    )
    fun getFavoriteWithAirports(): Flow<List<FavoriteWithAirports>>
}
