package com.example.flightsearchapp.feature.searchscreen.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.core.domain.DeleteFavoriteUseCase
import com.example.flightsearchapp.core.domain.GetAllFavoriteFlightsStreamUseCase
import com.example.flightsearchapp.core.domain.GetAllFlightsStreamUseCase
import com.example.flightsearchapp.core.domain.GetSavedSearchTextStreamUseCase
import com.example.flightsearchapp.core.domain.GetSuggestionsStreamUseCase
import com.example.flightsearchapp.core.domain.InsertFavoriteUseCase
import com.example.flightsearchapp.core.domain.SetSavedSearchTextUseCase
import com.example.flightsearchapp.core.ui.uistate.SearchScreenUiState
import com.example.flightsearchapp.core.ui.uistate.ShowFlightUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getAllFavoritesFlightsStreamUseCase: GetAllFavoriteFlightsStreamUseCase,
) : ViewModel() {

    private var _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var departureIdStream: MutableStateFlow<Long> = MutableStateFlow(-1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val showFlightUiState: StateFlow<ShowFlightUiState> =
        departureIdStream.flatMapLatest { departureId ->
            getAllFlightsStreamUseCase(departureId = departureId).mapLatest { flightModels ->
                if (flightModels.isEmpty()) {
                    ShowFlightUiState.Clear
                } else {
                    ShowFlightUiState.SelectSuggest(allFlights = flightModels)
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ShowFlightUiState.Clear,
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchScreenUiState: StateFlow<SearchScreenUiState> =
        getSavedSearchTextStreamUseCase()
            .flatMapLatest { savedSearchText ->
                departureIdStream.update {
                    -1L
                }
                if (savedSearchText.isEmpty()) {
                    getAllFavoritesFlightsStreamUseCase().flatMapLatest { favorites ->
                        flowOf(SearchScreenUiState.ShowFavorite(results = favorites))
                    }
                } else {
                    getSuggestionsStreamUseCase(query = savedSearchText)
                        .mapLatest { searchedApi ->
                            val sortedSearchedApi = searchedApi
                                .sortedByDescending { it.passengers }
                            SearchScreenUiState.ShowSuggests(
                                results = sortedSearchedApi,
                            )
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchScreenUiState.Init,
            )

    init {
        getInitSearchQuery()
    }

    @VisibleForTesting
    fun getInitSearchQuery() {
        viewModelScope.launch {
            _searchQuery.update { getSavedSearchTextStreamUseCase().firstOrNull() ?: "" }
        }
    }

    fun setSearchQuery(searchQuery: String) {
        _searchQuery.update { searchQuery }
    }

    fun setSavedSearchText(searchText: String) {
        viewModelScope.launch {
            setSavedSearchTextUseCase(searchText = searchText)
        }
    }

    fun setDepartureId(departureId: Long) {
        departureIdStream.update {
            departureId
        }
    }

    fun insertFavorite(departureCode: String, arriveCode: String) {
        viewModelScope.launch {
            insertFavoriteUseCase(
                departureCode = departureCode,
                arriveCode = arriveCode,
            )
        }
    }

    fun deleteFavorite(departureCode: String, arriveCode: String) {
        viewModelScope.launch {
            deleteFavoriteUseCase(
                departureCode = departureCode,
                arriveCode = arriveCode,
            )
        }
    }
}
