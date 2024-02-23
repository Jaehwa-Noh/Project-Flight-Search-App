package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FlightSearchApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            FlightSearchAppTopBar()
        }
    ) { contentPadding ->
        Text(
            "Content",
            modifier = Modifier.padding(contentPadding),
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