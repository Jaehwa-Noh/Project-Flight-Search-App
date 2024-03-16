package com.example.flightsearchapp.ui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.example.flightsearchapp.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() = hiltRule.inject()

    @Test
    fun whenOpenApp_FirstScreenIsSearchScreen() {
        composeTestRule.apply {
            onNodeWithText("Flight Search")
                .assertIsDisplayed()
        }
    }

    @Test
    fun whenSearchHAM_ShowSuggestions() {
        composeTestRule.apply {
            onNodeWithContentDescription("Search").apply {
                performClick()
                performTextInput("HAM")
                performImeAction()
            }

            /**
             * There are two "HAM" on the screen.
             * One is in the search bar
             * Another is in the suggestions.
             */
            onAllNodesWithText("HAM").assertCountEquals(2)

        }
    }
}
