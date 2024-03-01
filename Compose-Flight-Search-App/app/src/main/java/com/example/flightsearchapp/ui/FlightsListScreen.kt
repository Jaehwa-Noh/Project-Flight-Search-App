package com.example.flightsearchapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FlightsListScreen(
    modifier: Modifier = Modifier,
    searchScreenUiState: SearchScreenUiState
) {
    if (searchScreenUiState is SearchScreenUiState.SuccessSuggests) {

        Text("List ${searchScreenUiState.result}", modifier = modifier)

    }
}