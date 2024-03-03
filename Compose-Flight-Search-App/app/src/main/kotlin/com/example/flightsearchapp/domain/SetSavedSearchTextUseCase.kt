package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.SearchTextRepository
import com.example.flightsearchapp.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetSavedSearchTextUseCase @Inject constructor(
    private val searchTextRepository: SearchTextRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(searchText: String) {
        withContext(defaultDispatcher) {
            searchTextRepository.saveSearchText(searchText = searchText)
        }
    }
}