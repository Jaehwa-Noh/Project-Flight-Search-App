package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.Favorite
import com.example.flightsearchapp.ui.model.SearchedAirport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val airportsRepository: AirportsRepository
) : ViewModel() {
    private var _searchScreenUiState: MutableStateFlow<SearchScreenUiState> =
        MutableStateFlow(SearchScreenUiState.EmptySearch())

    val searchScreenUiState = _searchScreenUiState

    fun getSuggestsStream(query: String) {
        viewModelScope.launch {
            _searchScreenUiState.value = SearchScreenUiState.Loading
            airportsRepository.getSuggestionsStream(query)
                .catch { exception ->
                    _searchScreenUiState.value =
                        SearchScreenUiState.Error(message = exception.localizedMessage ?: "")
                }
                .collectLatest {
                    _searchScreenUiState.value = SearchScreenUiState.SuccessSuggests(result = it)
                }
        }
    }
}

sealed interface SearchScreenUiState {
    data class EmptySearch(val result: List<Favorite> = emptyList()) : SearchScreenUiState
    data object Loading : SearchScreenUiState
    data class Error(val message: String) : SearchScreenUiState
    data class SuccessSuggests(val result: List<SearchedAirport>) : SearchScreenUiState
    data class Select(val result: String) : SearchScreenUiState
}