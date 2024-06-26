package com.example.flightsearchapp.core.testing.fake.datasource

import com.example.flightsearchapp.core.data.datasource.FavoritesDataSource
import com.example.flightsearchapp.core.database.model.FavoriteEntity
import com.example.flightsearchapp.core.database.model.FavoriteWithAirports
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine

class FavoritesFakeDataSource(private val airportsFakeDataSource: AirportsFakeDataSource) :
    FavoritesDataSource {

    private val _favoriteEntitiesStream: MutableStateFlow<List<FavoriteEntity>> =
        MutableStateFlow(
            emptyList(),
        )

    val favoriteEntitiesStream: Flow<List<FavoriteEntity>> = _favoriteEntitiesStream.asSharedFlow()
    private val favorites = mutableListOf<FavoriteEntity>()

    override fun getFavoritesStream(): Flow<List<FavoriteEntity>> = favoriteEntitiesStream

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        favorites.add(favorite)
        _favoriteEntitiesStream.tryEmit(favorites)
    }

    override suspend fun deleteFavorite(departureCode: String, arriveCode: String) {
        val favoritesRemoveList = favorites.filter {
            it.departureCode == departureCode && it.destinationCode == arriveCode
        }
        favorites.removeAll(favoritesRemoveList)
        _favoriteEntitiesStream.tryEmit(favorites)
    }

    override fun getFavoriteWithAirports(): Flow<List<FavoriteWithAirports>> {
        return favoriteEntitiesStream.combine(
            airportsFakeDataSource.airportEntityStream,
        ) { favorites, airports ->
            if (favorites.isEmpty()) return@combine emptyList()
            if (airports.isEmpty()) return@combine emptyList()

            favorites.map { favorite ->

                val departureAirport = airports.filter { airport ->
                    favorite.departureCode == airport.iataCode
                }

                val arriveAirport = airports.filter { airport ->
                    favorite.destinationCode == airport.iataCode
                }

                if (departureAirport.isEmpty() ||
                    arriveAirport.isEmpty()
                ) {
                    return@combine emptyList()
                }

                FavoriteWithAirports(
                    favoriteEntity = favorite,
                    departureAirport = departureAirport.first(),
                    arriveAirport = arriveAirport.first(),
                )
            }
        }
    }
}
