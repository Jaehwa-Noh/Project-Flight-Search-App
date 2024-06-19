package com.example.flightsearchapp.core.testing.fake.repository

import com.example.flightsearchapp.core.data.repository.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchTextFakeHiltRepository @Inject constructor() :
    SearchTextRepository {

    private val mySearchText = MutableStateFlow("")

    override val savedSearchText: Flow<String> = mySearchText.asStateFlow()
    override suspend fun saveSearchText(searchText: String) {
        mySearchText.update { searchText }
    }
}
