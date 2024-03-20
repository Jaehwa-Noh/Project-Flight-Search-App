package com.example.flightsearchapp.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.example.flightsearchapp.MainActivity
import com.example.flightsearchapp.data.AirportsFtsRepository
import com.example.flightsearchapp.testing.database.airportFtsEntitiesTestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var airportsFtsRepository: AirportsFtsRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        runTest {
            airportsFtsRepository.deleteAndInsertAll(
                airportFtsEntitiesTestData
            )
        }
    }

    @Test
    fun whenOpenApp_FirstScreenIsSearchScreen() {
        composeTestRule.apply {
            onNodeWithText("Flight Search")
                .assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenSearchHAM_ShowSuggestions() {
        composeTestRule.apply {

            onNodeWithContentDescription("Search").apply {
                performClick()
                performTextClearance()
                performTextInput("HAM")
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

        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenSearchHAMAndClick_ShowFlights() {
        composeTestRule.apply {
            waitForIdle()
            onNodeWithContentDescription("Search").apply {
                performClick()
                performTextClearance()
                performTextInput("HAM")
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
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenSearchHAMAndClickAndBack_ShowSuggestion()  {
        composeTestRule.apply {
            onNodeWithContentDescription("Search").apply {
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
        }
    }
}
