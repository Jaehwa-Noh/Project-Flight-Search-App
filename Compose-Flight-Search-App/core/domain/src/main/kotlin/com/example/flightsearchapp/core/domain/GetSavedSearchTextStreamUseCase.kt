package com.example.flightsearchapp.core.domain

import com.example.flightsearchapp.core.data.repository.SearchTextRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedSearchTextStreamUseCase @Inject constructor(
    private val searchTextRepository: SearchTextRepository,
) {
    operator fun invoke(): Flow<String> = searchTextRepository.savedSearchText
}
