package com.example.flightsearchapp.ui.flight

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearchapp.ui.SearchScreenUiState
import com.example.flightsearchapp.ui.ShowFlightUiState
import com.example.flightsearchapp.ui.model.Flight
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun FlightsListScreen(
    modifier: Modifier = Modifier,
    showFlightUiState: ShowFlightUiState = ShowFlightUiState.Clear,
    searchScreenUiState: SearchScreenUiState = SearchScreenUiState.Init,
    onFavoriteClick: (
        departureIata: String,
        arriveIata: String,
        isBookmarked: Boolean,
    ) -> Unit,
) {
    if (showFlightUiState is ShowFlightUiState.SelectSuggest) {
        LazyColumn(modifier = modifier) {
            items(
                showFlightUiState.allFlights,
                key = {
                    it.id
                },
            ) { flight ->
                FlightItemCardUiComponent(
                    flight = flight,
                    onFavoriteClickAction = {
                        onFavoriteClick(
                            flight.departureIataCode,
                            flight.arriveIataCode,
                            flight.isBookmarked
                        )
                        true
                    },
                )
            }
        }
    }

    if (searchScreenUiState is SearchScreenUiState.ShowFavorite) {
        LazyColumn(modifier = modifier) {
            items(
                searchScreenUiState.results,
                key = {
                    it.id
                },
            ) { flight ->
                FlightItemCardUiComponent(
                    flight = flight,
                    onFavoriteClickAction = {
                        onFavoriteClick(
                            flight.departureIataCode,
                            flight.arriveIataCode,
                            flight.isBookmarked
                        )
                        true
                    },
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlightsListScreenPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        FlightsListScreen(
            showFlightUiState = ShowFlightUiState.SelectSuggest(
                allFlights = listOf(
                    Flight(
                        id = "1",
                        departureIataCode = "ABC",
                        departureName = "Departure Airport",
                        arriveIataCode = "EFG",
                        arriveName = "Arrive Airport",
                        isBookmarked = false,
                    ),
                    Flight(
                        id = "2",
                        departureIataCode = "DEF",
                        departureName = "Departure Airport",
                        arriveIataCode = "HIJ",
                        arriveName = "Arrive Airport",
                        isBookmarked = true,
                    ),
                ),
            ),
            onFavoriteClick = { _, _, _ -> },
        )
    }
}
