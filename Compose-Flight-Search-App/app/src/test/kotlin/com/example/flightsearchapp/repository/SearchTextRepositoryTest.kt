package com.example.flightsearchapp.repository

import com.example.flightsearchapp.data.SearchTextRepository
import com.example.flightsearchapp.fake.SearchTextFakeRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

/**
 * Instrumented test for [InDiskSearchTextRepository][SearchTextRepository]
 */
class SearchTextRepositoryTest {

    private lateinit var searchTextRepository: SearchTextRepository

    @BeforeTest
    fun setUp() {
        searchTextRepository = SearchTextFakeRepository()
    }

    @Test
    fun searchQuery_saveABC_matchResult() = runTest {
        searchTextRepository.saveSearchText("ABC")
        val text = searchTextRepository.savedSearchText.first()
        assertEquals("ABC", text)
    }

    @Test
    fun firstOpen_empty_matchResult() = runTest {
        val text = searchTextRepository.savedSearchText.first()
        assertEquals("", text)
    }
}
