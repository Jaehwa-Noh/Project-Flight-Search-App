package com.example.flightsearchapp.ui.suggestion

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.ui.model.SuggestionAirport
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun SuggestionsListScreen(
    modifier: Modifier = Modifier,
    suggestionAirportModels: List<SuggestionAirport>,
    onSuggestionClick: (Long) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(suggestionAirportModels)
        { searchedAirport ->
            SuggestionsListItemUiComponent(
                suggestionAirportModel = searchedAirport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                onItemClick = onSuggestionClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestListScreenPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        SuggestionsListScreen(
            suggestionAirportModels = listOf(
                SuggestionAirport(
                    id = 1,
                    iataCode = "ABC",
                    name = "Airport"
                ),
                SuggestionAirport(
                    id = 2,
                    iataCode = "DEF",
                    name = "Airport2"
                ),
            ),
            onSuggestionClick = {}
        )
    }
}