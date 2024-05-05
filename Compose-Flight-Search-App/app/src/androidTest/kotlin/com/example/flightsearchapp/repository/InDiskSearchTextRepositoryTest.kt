package com.example.flightsearchapp.repository

import com.example.flightsearchapp.data.SearchTextRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class InDiskSearchTextRepositoryTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var searchTextRepository: SearchTextRepository

    @Inject
    lateinit var testScope: TestScope

    @Before
    fun setUp() = hiltRule.inject()

    @Test
    fun searchQuery_saveABC_matchResult() = testScope.runTest {
        searchTextRepository.saveSearchText("ABC")
        val text = searchTextRepository.savedSearchText.first()
        assertEquals("ABC", text)
    }

    @Test
    fun firstOpen_empty_matchResult() = testScope.runTest {
        val text = searchTextRepository.savedSearchText.first()
        assertEquals("", text)
    }
}
