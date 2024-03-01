package com.example.flightsearchapp.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearchapp.ui.model.FlightModel
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun FlightsListScreen(
    modifier: Modifier = Modifier,
    showFlightUiState: ShowFlightUiState,
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
                    onFavoriteClick = onFavoriteClick
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
                    FlightModel(
                        id = "1",
                        departureIataCode = "ABC",
                        departureName = "Departure Airport",
                        arriveIataCode = "EFG",
                        arriveName = "Arrive Airport",
                        isBookmarked = false,
                    ),
                    FlightModel(
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