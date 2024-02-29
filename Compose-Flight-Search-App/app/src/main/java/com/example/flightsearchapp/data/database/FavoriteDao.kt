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
    fun getAllFavoritesStream(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Query(
        """
            DELETE FROM favorite
             WHERE id = :favoriteId
        """
    )
    fun delete(favoriteId: Long)
}