package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.database.Favorite
import com.example.flightsearchapp.domain.GetSavedSearchTextStreamUseCase
import com.example.flightsearchapp.domain.GetSuggestionsStreamUseCase
import com.example.flightsearchapp.domain.SetSavedSearchTextUseCase
import com.example.flightsearchapp.ui.model.SearchedAirport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSavedSearchTextStreamUseCase: GetSavedSearchTextStreamUseCase,
    private val setSavedSearchTextUseCase: SetSavedSearchTextUseCase,
    private val getSuggestionsStreamUseCase: GetSuggestionsStreamUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchScreenUiState: StateFlow<SearchScreenUiState> =
        getSavedSearchTextStreamUseCase()
            .flatMapLatest { savedSearchText ->
                if (savedSearchText.isEmpty()) {
                    flowOf(SearchScreenUiState.ShowFavorite())
                } else {
                    getSuggestionsStreamUseCase(query = savedSearchText)
                        .mapLatest { searchedApi ->
                            SearchScreenUiState.ShowSuggests(
                                result = searchedApi
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
}

sealed interface SearchScreenUiState {
    data class ShowFavorite(val result: List<Favorite> = emptyList()) : SearchScreenUiState
    data object Loading : SearchScreenUiState
    data class Error(val message: String) : SearchScreenUiState
    data class ShowSuggests(val result: List<SearchedAirport>) : SearchScreenUiState
    data class Select(val result: String) : SearchScreenUiState
    data object Init : SearchScreenUiState
}