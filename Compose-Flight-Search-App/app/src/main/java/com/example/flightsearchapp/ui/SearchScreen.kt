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

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        FlightSearchBar(modifier = Modifier.fillMaxWidth())
        SearchFlightListScreen(modifier = Modifier.fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(modifier: Modifier = Modifier) {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
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