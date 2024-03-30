package com.example.flightsearchapp.testing.repository

import com.example.flightsearchapp.data.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TestSearchTextRepository @Inject constructor(): SearchTextRepository {

    private val mySearchText = MutableStateFlow("")

    override val savedSearchText: Flow<String> = mySearchText.asStateFlow()
    override suspend fun saveSearchText(searchText: String) {
        mySearchText.update { searchText }
    }
}
