package com.example.flightsearchapp.domain

import com.example.flightsearchapp.data.SearchTextRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSavedSearchTextStreamUseCase @Inject constructor(
    private val searchTextRepository: SearchTextRepository,
) {
    operator fun invoke(): Flow<String> = searchTextRepository.savedSearchText
}
