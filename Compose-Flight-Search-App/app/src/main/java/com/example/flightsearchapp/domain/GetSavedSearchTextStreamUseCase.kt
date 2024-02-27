package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedSearchTextStreamUseCase @Inject constructor(
    private val searchTextRepository: SearchTextRepository,
) {
    operator fun invoke(): Flow<String> =
        searchTextRepository.savedSearchText
}