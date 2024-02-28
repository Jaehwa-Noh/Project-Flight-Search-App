package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.ui.model.SearchedAirport
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun SuggestionsListScreen(
    modifier: Modifier = Modifier,
    searchedAirports: List<SearchedAirport>,
) {
    LazyColumn(modifier = modifier) {
        items(searchedAirports)
        { searchedAirport ->
            SuggestionsListItem(
                searchedAirport = searchedAirport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestListScreenPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        SuggestionsListScreen(
            searchedAirports = listOf(
                SearchedAirport(
                    id = 1,
                    iataCode = "ABC",
                    name = "Airport"
                ),
                SearchedAirport(
                    id = 2,
                    iataCode = "DEF",
                    name = "Airport2"
                ),
            )
        )
    }
}