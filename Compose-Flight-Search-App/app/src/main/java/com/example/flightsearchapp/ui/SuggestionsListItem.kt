package com.example.flightsearchapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearchapp.ui.model.SuggestionAirportModel
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun SuggestionsListItem(
    modifier: Modifier = Modifier,
    suggestionAirportModel: SuggestionAirportModel,
    onItemClick: (Long) -> Unit
) {

    AirportInfoUiComponent(iataCode = suggestionAirportModel.iataCode,
        name = suggestionAirportModel.name,
        modifier = modifier
            .clickable {
                onItemClick(suggestionAirportModel.id)
            }
    )
}


@Preview(showBackground = true)
@Composable
fun SuggestListItemPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        SuggestionsListItem(
            suggestionAirportModel = SuggestionAirportModel(
                id = 1,
                iataCode = "ABC",
                name = "Airport"
            ),
            onItemClick = {},
        )
    }
}