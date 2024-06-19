package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.di.DispatcherDefault
import com.example.flightsearchapp.core.data.repository.SearchTextRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
