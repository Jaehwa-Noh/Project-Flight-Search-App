package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchTextFakeRepository() : SearchTextRepository {

    private val _searchTextStream = MutableStateFlow("")
    val searchTextStream = _searchTextStream.asStateFlow()

    override val savedSearchText: Flow<String> = searchTextStream

    override suspend fun saveSearchText(searchText: String) {
        _searchTextStream.update { searchText }
    }
}
