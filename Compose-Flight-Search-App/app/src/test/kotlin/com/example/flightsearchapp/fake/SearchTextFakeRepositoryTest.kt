package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.SearchTextRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Instrumented test for [SearchTextFakeRepository]
 */
class SearchTextFakeRepositoryTest {
    private lateinit var searchTextRepository: SearchTextRepository

    @BeforeTest
    fun setUp() {
        searchTextRepository = SearchTextFakeRepository()
    }

    @Test
    fun null_init_empty() = runTest {
        val result = searchTextRepository.savedSearchText.first()

        assertEquals("", result)
    }

    @Test
    fun text_setBest_matchText() = runTest {
        searchTextRepository.saveSearchText("Best")
        val result = searchTextRepository.savedSearchText.first()

        assertEquals("Best", result)
    }

    @Test
    fun text_change_matchText() = runTest {
        searchTextRepository.saveSearchText("Best")
        var result = searchTextRepository.savedSearchText.first()

        assertEquals("Best", result)

        searchTextRepository.saveSearchText("Super")
        result = searchTextRepository.savedSearchText.first()

        assertEquals("Super", result)
    }
}
