package com.example.flightsearchapp.core.ui.uistate

import com.example.flightsearchapp.core.model.Flight
import com.example.flightsearchapp.core.model.SuggestionAirport

sealed interface SearchScreenUiState {
    data class ShowFavorite(val results: List<Flight>) : SearchScreenUiState
    data class ShowSuggests(val results: List<SuggestionAirport>) :
        SearchScreenUiState
    data object Init : SearchScreenUiState
}
