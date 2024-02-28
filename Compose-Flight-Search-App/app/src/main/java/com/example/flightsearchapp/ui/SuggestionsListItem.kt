package com.example.flightsearchapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.ui.model.SearchedAirport
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun SuggestionsListItem(
    modifier: Modifier = Modifier,
    searchedAirport: SearchedAirport,
    onItemClick: (Long) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onItemClick(searchedAirport.id)
            }
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = searchedAirport.iataCode,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = searchedAirport.name)
    }

}


@Preview(showBackground = true)
@Composable
fun SuggestListItemCardPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        SuggestionsListItem(
            searchedAirport = SearchedAirport(
                id = 1,
                iataCode = "ABC",
                name = "Airport"
            ),
            onItemClick = {},
        )
    }
}