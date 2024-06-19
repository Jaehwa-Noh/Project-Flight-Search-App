package com.example.flightsearchapp.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.example.flightsearchapp.MainActivity
import com.example.flightsearchapp.core.testing.fake.rules.GrantPostNotificationsPermissionRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * UI test for Navigation
 */
@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val permissionRule = GrantPostNotificationsPermissionRule()

    @Before
    fun setUp() = hiltRule.inject()

    @Test
    fun screen_openApp_firstScreenIsSearchScreen() {
        composeTestRule.apply {
            onNodeWithText("Flight Search")
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchQuery_searchHAM_showSuggestions() {
        composeTestRule.apply {
            onNodeWithTag("Search bar").onChild().apply {
                performClick()
                performTextClearance()
                performTextInput("HAM")
            }

            /*
             * There are two "HAM" on the screen.
             * First is in the search bar
             * Another is in the suggestions.
             */
            waitUntilNodeCount(hasText("HAM"), 2, 10_000)
            onNodeWithText("Hamburg Airport").apply {
                performClick()
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchQuery_searchHAMAndClick_showFlights() {
        composeTestRule.apply {
            waitForIdle()
            onNodeWithTag("Search bar").onChild().apply {
                performClick()
                performTextClearance()
                performTextInput("HAM")
            }

            /*
             * There are two "HAM" on the screen.
             * First is in the search bar
             * Another is in the suggestions.
             */
            waitUntilNodeCount(hasText("HAM"), 2, 10_000)
            onNodeWithText("Hamburg Airport").apply {
                performClick()
            }

            onNodeWithText("Flights from HAM")
                .assertIsDisplayed()

            onNodeWithText("SVO")
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchQuery_searchHAMAndClickAndBack_showSuggestion() {
        composeTestRule.apply {
            onNodeWithTag("Search bar").onChild().apply {
                performClick()
                performTextClearance()
                performTextInput("HAM")
                performImeAction()
            }

            /**
             * There are two "HAM" on the screen.
             * First is in the search bar
             * Another is in the suggestions.
             */
            waitUntilNodeCount(hasText("HAM"), 2, 10_000)
            onNodeWithText("Hamburg Airport").apply {
                performClick()
            }

            onNodeWithText("Flights from HAM")
                .assertIsDisplayed()

            onNodeWithText("SVO")
                .assertIsDisplayed()

            Espresso.pressBack()

            waitUntilNodeCount(hasText("Hamburg Airport"), 1)

            onNodeWithText("Hamburg Airport")
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test(expected = NoActivityResumedException::class)
    fun searchQuery_searchHAMAndClickAndTwiceBack_exitApp() {
        composeTestRule.apply {
            onNodeWithTag("Search bar").onChild().apply {
                performClick()
                performTextClearance()
                performTextInput("HAM")
                performImeAction()
            }

            /**
             * There are two "HAM" on the screen.
             * First is in the search bar
             * Another is in the suggestions.
             */
            waitUntilNodeCount(hasText("HAM"), 2, 10_000)
            onNodeWithText("Hamburg Airport").apply {
                performClick()
            }

            onNodeWithText("Flights from HAM")
                .assertIsDisplayed()

            onNodeWithText("SVO")
                .assertIsDisplayed()

            Espresso.pressBack()
            onNodeWithText("Hamburg Airport")
                .assertIsDisplayed()

            Espresso.pressBack()
        }
    }
}
