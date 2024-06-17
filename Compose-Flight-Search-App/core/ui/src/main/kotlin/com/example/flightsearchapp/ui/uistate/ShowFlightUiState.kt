package com.example.flightsearchapp.ui.uistate

sealed interface ShowFlightUiState {
    data object Clear : ShowFlightUiState
    data class SelectSuggest(val allFlights: List<com.example.flightsearchapp.ui.model.Flight>) : ShowFlightUiState
}
