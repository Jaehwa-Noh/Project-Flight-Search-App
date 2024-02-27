package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.SearchTextRepository
import com.example.flightsearchapp.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSavedSearchTextStreamUseCase @Inject constructor(
    private val searchTextRepository: SearchTextRepository,
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<String> {
        return withContext(defaultDispatcher) {
            searchTextRepository.savedSearchText
        }
    }
}