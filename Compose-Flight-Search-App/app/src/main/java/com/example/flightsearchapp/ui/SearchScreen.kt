package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchScreenViewModel: SearchScreenViewModel = viewModel()
    val searchScreenUiState =
        searchScreenViewModel.searchScreenUiState.collectAsStateWithLifecycle()
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

    Column(modifier = modifier) {
        FlightSearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchQuery.value,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
        )
        SearchFlightListScreen(
            modifier = Modifier.fillMaxSize(),
            searchScreenUiState = searchScreenUiState.value,
        )
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
        modifier = modifier,
    ) {

    }
}