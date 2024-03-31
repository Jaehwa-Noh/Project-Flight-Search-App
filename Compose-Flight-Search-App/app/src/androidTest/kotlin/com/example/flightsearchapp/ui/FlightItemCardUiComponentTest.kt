package com.example.flightsearchapp.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.flightsearchapp.testing.model.ui.bookMarkToggle
import com.example.flightsearchapp.testing.model.ui.flightTestData
import com.example.flightsearchapp.ui.flight.FlightItemCardUiComponent
import org.junit.Rule
import org.junit.Test

class FlightItemCardUiComponentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun flight_WhenNoFavorite_NoFavorite() {
        composeTestRule.apply {
            setContent {
                FlightItemCardUiComponent(
                    flight = flightTestData,
                    onFavoriteClickAction = { true },
                )
            }

            onNodeWithText(flightTestData.arriveIataCode).assertIsDisplayed()
            onNodeWithText(flightTestData.arriveName).assertIsDisplayed()
            onNodeWithText(flightTestData.departureIataCode).assertIsDisplayed()
            onNodeWithText(flightTestData.departureName).assertIsDisplayed()
            onNodeWithContentDescription("Is not Favorite").assertIsDisplayed()
            onNodeWithContentDescription("Favorite").assertIsNotDisplayed()
        }
    }

    @Test
    fun flight_WhenFavorite_Favorite() {
        composeTestRule.apply {
            setContent {
                FlightItemCardUiComponent(
                    flight = flightTestData.bookMarkToggle(),
                    onFavoriteClickAction = { true },
                )
            }

            onNodeWithText(flightTestData.arriveIataCode).assertIsDisplayed()
            onNodeWithText(flightTestData.arriveName).assertIsDisplayed()
            onNodeWithText(flightTestData.departureIataCode).assertIsDisplayed()
            onNodeWithText(flightTestData.departureName).assertIsDisplayed()
            onNodeWithContentDescription("Is not Favorite").assertIsNotDisplayed()
            onNodeWithContentDescription("Favorite").assertIsDisplayed()
        }
    }
}