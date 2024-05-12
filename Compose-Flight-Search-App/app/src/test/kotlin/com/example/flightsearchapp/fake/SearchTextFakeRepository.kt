package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchTextFakeRepository() : SearchTextRepository {

    private val _searchTextStream = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val searchTextStream = _searchTextStream.asSharedFlow()

    override val savedSearchText: Flow<String> = searchTextStream

    init {
        _searchTextStream.tryEmit("")
    }

    override suspend fun saveSearchText(searchText: String) {
        _searchTextStream.tryEmit(searchText)
    }
}
