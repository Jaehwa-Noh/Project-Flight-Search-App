package com.example.flightsearchapp.core.data.repository

import com.example.flightsearchapp.core.testing.fake.datasource.SearchTextFakeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
