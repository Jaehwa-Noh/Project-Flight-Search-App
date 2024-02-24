package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlightSearchApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FlightSearchAppTopBar()
        }
    ) { contentPadding ->
        SearchScreen(
            modifier
                .padding(contentPadding)
                .padding(horizontal = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchAppTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(
            "Flight Search",
            modifier = modifier,
        )
    })
}