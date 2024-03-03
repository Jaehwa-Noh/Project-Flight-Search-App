package com.example.flightsearchapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query(
        """
            SELECT *
              FROM favorite
        """
    )
    fun getAllFavoritesStream(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntity)

    @Query(
        """
            DELETE FROM favorite
             WHERE departure_code = :departureCode AND destination_code = :arriveCode
        """
    )
    fun delete(departureCode: String, arriveCode: String)
}