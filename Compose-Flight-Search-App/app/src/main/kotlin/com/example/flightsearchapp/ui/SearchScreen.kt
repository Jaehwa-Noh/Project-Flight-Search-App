package com.example.flightsearchapp.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.R
import com.example.flightsearchapp.ui.flight.FlightsListScreen
import com.example.flightsearchapp.ui.suggestion.SuggestionsListScreen

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchScreenViewModel: SearchScreenViewModel = viewModel()
    val searchScreenUiState =
        searchScreenViewModel.searchScreenUiState.collectAsStateWithLifecycle()
    val showFlightUiState =
        searchScreenViewModel.showFlightUiState.collectAsStateWithLifecycle()
    val searchQuery =
        searchScreenViewModel.searchQuery.collectAsStateWithLifecycle(initialValue = "")

    val focusManager = LocalFocusManager.current

    val onQueryChange: (String) -> Unit = { currentSearchQuery ->
        searchScreenViewModel.setSearchQuery(currentSearchQuery)
        searchScreenViewModel.setSavedSearchText(searchText = currentSearchQuery)
    }

    val onSearch: (String) -> Unit = {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    val activity = (LocalContext.current as? Activity)

    BackHandler {
        if (showFlightUiState.value !is ShowFlightUiState.SelectSuggest) {
            activity?.finish()
            return@BackHandler
        }
        searchScreenViewModel.setDepartureId(-1)
    }

    Column(modifier = modifier) {
        FlightSearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchQuery.value,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
        )

        Spacer(
            modifier = Modifier.height(8.dp),
        )

        when (showFlightUiState.value) {
            is ShowFlightUiState.Clear -> {
                when (searchScreenUiState.value) {
                    is SearchScreenUiState.ShowSuggests -> {
                        SuggestionsListScreen(
                            suggestionAirportModels =
                            (searchScreenUiState.value as SearchScreenUiState.ShowSuggests).results,
                            onSuggestionClick = {
                                onSearch("")
                                searchScreenViewModel.setDepartureId(it)
                            },
                        )
                    }

                    is SearchScreenUiState.ShowFavorite -> {
                        Text(
                            "Favorite routes",
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .semantics { heading() },
                        )

                        FlightsListScreen(
                            modifier = Modifier.fillMaxSize(),
                            searchScreenUiState = searchScreenUiState.value,
                            onFavoriteClick = { departureIata, arriveIata, isBookmarked: Boolean ->
                                if (isBookmarked) {
                                    searchScreenViewModel.deleteFavorite(
                                        departureCode = departureIata,
                                        arriveCode = arriveIata,
                                    )
                                } else {
                                    searchScreenViewModel.insertFavorite(
                                        departureCode = departureIata,
                                        arriveCode = arriveIata,
                                    )
                                }
                            },
                        )
                    }

                    else -> Unit
                }
            }

            is ShowFlightUiState.SelectSuggest -> {
                Text(
                    "Flights from ${
                        (showFlightUiState.value as ShowFlightUiState.SelectSuggest)
                            .allFlights
                            .first()
                            .departureIataCode
                    }",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .semantics {
                            heading()
                        },
                )
                FlightsListScreen(
                    modifier = Modifier.fillMaxSize(),
                    showFlightUiState = showFlightUiState.value,
                    onFavoriteClick = { departureIata, arriveIata, isBookmarked: Boolean ->
                        if (isBookmarked) {
                            searchScreenViewModel.deleteFavorite(
                                departureCode = departureIata,
                                arriveCode = arriveIata,
                            )
                        } else {
                            searchScreenViewModel.insertFavorite(
                                departureCode = departureIata,
                                arriveCode = arriveIata,
                            )
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
            )
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_bar_placeholder))
        },
        modifier = modifier
            .testTag("Search bar"),
    ) {
    }
}
