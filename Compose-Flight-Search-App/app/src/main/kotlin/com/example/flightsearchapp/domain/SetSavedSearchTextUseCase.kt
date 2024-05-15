package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.SearchTextRepository
import com.example.flightsearchapp.di.DispatcherDefault
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SetSavedSearchTextUseCase @Inject constructor(
    private val searchTextRepository: SearchTextRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(searchText: String) {
        withContext(defaultDispatcher) {
            searchTextRepository.saveSearchText(searchText = searchText)
        }
    }
}
