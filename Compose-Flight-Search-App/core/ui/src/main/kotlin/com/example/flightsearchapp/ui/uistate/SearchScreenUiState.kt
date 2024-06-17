package com.example.flightsearchapp.ui.uistate

sealed interface SearchScreenUiState {
    data class ShowFavorite(val results: List<com.example.flightsearchapp.ui.model.Flight>) : SearchScreenUiState
    data class ShowSuggests(val results: List<com.example.flightsearchapp.data.model.SuggestionAirport>) : SearchScreenUiState
    data object Init : SearchScreenUiState
}
