package com.example.flightsearchapp.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.ui.model.FlightModel
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun FlightItemCardUiComponent(
    modifier: Modifier = Modifier,
    flight: FlightModel,
) {
    Card(
        onClick = { },
        modifier = modifier
            .padding(8.dp)
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
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,

                    contentDescription =
                    if (flight.isBookmarked) "Favorite"
                    else null,

                    tint =
                    if (flight.isBookmarked) Color.Green
                    else Color.LightGray,
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
            flight = FlightModel(
                id = "1",
                departureIataCode = "ABC",
                departureName = "Departure Airport",
                arriveIataCode = "EFG",
                arriveName = "Arrive Airport",
                isBookmarked = false,
            )
        )
    }
}