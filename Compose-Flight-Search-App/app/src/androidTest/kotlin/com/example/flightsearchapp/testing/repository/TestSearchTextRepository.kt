package com.example.flightsearchapp.testing.repository

import com.example.flightsearchapp.data.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestSearchTextRepository: SearchTextRepository {
    override val savedSearchText: Flow<String> = flowOf()
    override suspend fun saveSearchText(searchText: String) = Unit
}
