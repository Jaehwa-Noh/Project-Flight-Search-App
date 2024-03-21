package com.example.flightsearchapp.ui.flight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.ui.AirportInfoUiComponent
import com.example.flightsearchapp.ui.model.Flight
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun FlightItemCardUiComponent(
    modifier: Modifier = Modifier,
    flight: Flight,
    onFavoriteClickAction: () -> Boolean,
) {
    val favoriteActionLabel = if (flight.isBookmarked) "Remove Favorite" else "Add Favorite"
    val favoriteStateLabel = if (flight.isBookmarked) "Favorite" else "Is not Favorite"

    Card(
        modifier = modifier
            .padding(8.dp)
            .semantics(mergeDescendants = true) {
                customActions = listOf(
                    CustomAccessibilityAction(favoriteActionLabel, onFavoriteClickAction)
                )

                stateDescription = favoriteStateLabel

            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    "DEPART",
                    style = MaterialTheme.typography.labelMedium,
                )

                AirportInfoUiComponent(
                    iataCode = flight.departureIataCode,
                    name = flight.departureName,
                )

                Text(
                    "ARRIVE",
                    style = MaterialTheme.typography.labelMedium,
                )

                AirportInfoUiComponent(
                    iataCode = flight.arriveIataCode,
                    name = flight.arriveName,
                )
            }

            IconButton(
                onClick = {
                    onFavoriteClickAction()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,

                    contentDescription = null,
//                    if (flight.isBookmarked) "Favorite"
//                    else "Not Favorite",

                    tint =
                    if (flight.isBookmarked) Color.Green
                    else Color.LightGray,
                    modifier = Modifier
                        .clearAndSetSemantics {
                            contentDescription = favoriteActionLabel
                        },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightItemCardUiComponentPreview() {
    FlightSearchAppTheme(dynamicColor = false) {
        FlightItemCardUiComponent(
            flight = Flight(
                id = "1",
                departureIataCode = "ABC",
                departureName = "Departure Airport",
                arriveIataCode = "EFG",
                arriveName = "Arrive Airport",
                isBookmarked = false,
            ),
            onFavoriteClickAction = { true },
        )
    }
}
