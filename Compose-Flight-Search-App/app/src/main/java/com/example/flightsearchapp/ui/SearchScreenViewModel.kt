package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.database.Favorite
import com.example.flightsearchapp.domain.GetAllFlightsStreamUseCase
import com.example.flightsearchapp.domain.GetSavedSearchTextStreamUseCase
import com.example.flightsearchapp.domain.GetSuggestionsStreamUseCase
import com.example.flightsearchapp.domain.SetSavedSearchTextUseCase
import com.example.flightsearchapp.ui.model.FlightModel
import com.example.flightsearchapp.ui.model.SuggestionAirportModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSavedSearchTextStreamUseCase: GetSavedSearchTextStreamUseCase,
    private val setSavedSearchTextUseCase: SetSavedSearchTextUseCase,
    private val getSuggestionsStreamUseCase: GetSuggestionsStreamUseCase,
    private val getAllFlightsStreamUseCase: GetAllFlightsStreamUseCase,
) : ViewModel() {

    private var _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var _showFlightUiState = MutableStateFlow<ShowFlightUiState>(ShowFlightUiState.Clear)
    val showFlightUiState = _showFlightUiState.asStateFlow()

    init {
        getInitSearchQuery()
    }

    private fun getInitSearchQuery() {
        viewModelScope.launch {
            _searchQuery.update { getSavedSearchTextStreamUseCase().firstOrNull() ?: "" }
        }
    }

    fun setSearchQuery(searchQuery: String) {
        _searchQuery.update { searchQuery }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchScreenUiState: StateFlow<SearchScreenUiState> =
        getSavedSearchTextStreamUseCase()
            .flatMapLatest { savedSearchText ->
                _showFlightUiState.value = ShowFlightUiState.Clear
                if (savedSearchText.isEmpty()) {
                    flowOf(SearchScreenUiState.ShowFavorite())
                } else {
                    getSuggestionsStreamUseCase(query = savedSearchText)
                        .mapLatest { searchedApi ->
                            SearchScreenUiState.ShowSuggests(
                                results = searchedApi
                            )
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchScreenUiState.Init
            )

    fun setSavedSearchText(searchText: String) {
        viewModelScope.launch {
            setSavedSearchTextUseCase(searchText = searchText)
        }
    }

    fun getAllFlights(departureId: Long) {
        viewModelScope.launch {
            _showFlightUiState.value =
                ShowFlightUiState.SelectSuggest(
                    allFlights = getAllFlightsStreamUseCase(departureId = departureId).first()
                )
        }
    }
}

sealed interface SearchScreenUiState {
    data class ShowFavorite(val results: List<Favorite> = emptyList()) : SearchScreenUiState
    data class ShowSuggests(val results: List<SuggestionAirportModel>) : SearchScreenUiState
    data object Init : SearchScreenUiState
}

sealed interface ShowFlightUiState {
    data object Clear : ShowFlightUiState
    data class SelectSuggest(val allFlights: List<FlightModel>) : ShowFlightUiState
}