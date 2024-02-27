package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import com.example.flightsearchapp.data.database.Favorite
import com.example.flightsearchapp.domain.GetSavedSearchTextStreamUseCase
import com.example.flightsearchapp.domain.GetSuggestionsStreamUseCase
import com.example.flightsearchapp.domain.SetSavedSearchTextUseCase
import com.example.flightsearchapp.ui.model.SearchedAirport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSavedSearchTextStreamUseCase: GetSavedSearchTextStreamUseCase,
    private val setSavedSearchTextUseCase: SetSavedSearchTextUseCase,
    private val getSuggestionsStreamUseCase: GetSuggestionsStreamUseCase,
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