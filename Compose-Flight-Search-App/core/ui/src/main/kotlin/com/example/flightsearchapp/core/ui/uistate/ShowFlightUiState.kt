package com.example.flightsearchapp.core.ui.uistate

import com.example.flightsearchapp.core.ui.model.Flight

sealed interface ShowFlightUiState {
    data object Clear : ShowFlightUiState
    data class SelectSuggest(val allFlights: List<Flight>) : ShowFlightUiState
}
