package com.example.flightsearchapp.core.ui.suggestion

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearchapp.core.designsystem.theme.FlightSearchAppTheme
import com.example.flightsearchapp.core.model.SuggestionAirport
import com.example.flightsearchapp.core.ui.AirportInfoUiComponent

@Composable
fun SuggestionsListItemUiComponent(
    modifier: Modifier = Modifier,
    suggestionAirportModel: SuggestionAirport,
    onItemClick: (Long) -> Unit,
) {
    AirportInfoUiComponent(
        iataCode = suggestionAirportModel.iataCode,
        name = suggestionAirportModel.name,
        modifier = modifier
            .clickable {
                onItemClick(suggestionAirportModel.id)
            },
    )
}

@Preview(showBackground = true)
@Composable
fun SuggestListItemUiComponentPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        SuggestionsListItemUiComponent(
            suggestionAirportModel = SuggestionAirport(
                id = 1,
                iataCode = "ABC",
                name = "Airport",
                passengers = 10,
            ),
            onItemClick = {},
        )
    }
}
