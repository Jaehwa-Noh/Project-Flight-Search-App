package com.example.flightsearchapp.feature.searchscreen

import com.example.flightsearchapp.core.data.model.asFtsEntity
import com.example.flightsearchapp.core.data.repository.AllFlightsRepository
import com.example.flightsearchapp.core.data.repository.InDiskAirportsFtsRepository
import com.example.flightsearchapp.core.data.repository.InDiskAirportsRepository
import com.example.flightsearchapp.core.data.repository.InDiskFavoritesRepository
import com.example.flightsearchapp.core.domain.DeleteFavoriteUseCase
import com.example.flightsearchapp.core.domain.GetAllFavoriteFlightsStreamUseCase
import com.example.flightsearchapp.core.domain.GetAllFlightsStreamUseCase
import com.example.flightsearchapp.core.domain.GetSavedSearchTextStreamUseCase
import com.example.flightsearchapp.core.domain.GetSuggestionsStreamUseCase
import com.example.flightsearchapp.core.domain.InsertFavoriteUseCase
import com.example.flightsearchapp.core.domain.SetSavedSearchTextUseCase
import com.example.flightsearchapp.core.testing.fake.data.database.airportEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.data.database.favoriteEntitiesTestData
import com.example.flightsearchapp.core.testing.fake.datasource.AirportsFakeDataSource
import com.example.flightsearchapp.core.testing.fake.datasource.AirportsFtsFakeDataSource
import com.example.flightsearchapp.core.testing.fake.datasource.FavoritesFakeDataSource
import com.example.flightsearchapp.core.testing.fake.datasource.SearchTextFakeRepository
import com.example.flightsearchapp.core.ui.uistate.SearchScreenUiState
import com.example.flightsearchapp.core.ui.uistate.ShowFlightUiState
import com.example.flightsearchapp.feature.searchscreen.rule.MainTestDispatcherRule
import com.example.flightsearchapp.feature.searchscreen.ui.SearchScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * Unit test for [SearchScreenViewModel]
 */
class SearchScreenViewModelTest {
    @get:Rule
    val mainTestDispatcherRule = MainTestDispatcherRule()

    private lateinit var searchScreenViewModel: SearchScreenViewModel

    private val searchTextFakeRepository =
        SearchTextFakeRepository()

    private val airportsFakeDataSource =
        AirportsFakeDataSource()

    private val airportsFtsFakeDataSource =
        AirportsFtsFakeDataSource()

    private val favoritesFakeDataSource =
        FavoritesFakeDataSource(
            airportsFakeDataSource
        )

    private val airportsFtsRepository =
        InDiskAirportsFtsRepository(
            airportsFtsDataSource = airportsFtsFakeDataSource,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val airportsRepository =
        InDiskAirportsRepository(
            airportsDataSource = airportsFakeDataSource,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val favoritesRepository =
        InDiskFavoritesRepository(
            favoritesDataSource = favoritesFakeDataSource,
        )

    private val allFlightsRepository =
        AllFlightsRepository(
            airportsRepository = airportsRepository,
            favoritesRepository = favoritesRepository,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val getSavedSearchTextStreamUseCase =
        GetSavedSearchTextStreamUseCase(
            searchTextRepository = searchTextFakeRepository,
        )

    private val setSavedSearchTextUseCase =
        SetSavedSearchTextUseCase(
            searchTextRepository = searchTextFakeRepository,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val getSuggestionsStreamUseCase =
        GetSuggestionsStreamUseCase(
            airportsRepository = airportsRepository,
            airportsFtsRepository = airportsFtsRepository,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val getAllFlightsStreamUseCase =
        GetAllFlightsStreamUseCase(
            allFlightsRepository = AllFlightsRepository(
                airportsRepository = airportsRepository,
                favoritesRepository = favoritesRepository,
                defaultDispatcher = mainTestDispatcherRule.testDispatcher,
            ),
        )

    private val insertFavoriteUseCase =
        InsertFavoriteUseCase(
            favoritesRepository = favoritesRepository,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val deleteFavoriteUseCase =
        DeleteFavoriteUseCase(
            favoritesRepository = favoritesRepository,
            defaultDispatcher = mainTestDispatcherRule.testDispatcher,
        )

    private val getAllFavoritesFlightsStreamUseCase =
        GetAllFavoriteFlightsStreamUseCase(
            getAllFlightsRepository = allFlightsRepository,
        )

    @BeforeTest
    fun createSearchScreenViewModel() {
        searchScreenViewModel =
            SearchScreenViewModel(
                getSavedSearchTextStreamUseCase = getSavedSearchTextStreamUseCase,
                setSavedSearchTextUseCase = setSavedSearchTextUseCase,
                getSuggestionsStreamUseCase = getSuggestionsStreamUseCase,
                getAllFlightsStreamUseCase = getAllFlightsStreamUseCase,
                insertFavoriteUseCase = insertFavoriteUseCase,
                deleteFavoriteUseCase = deleteFavoriteUseCase,
                getAllFavoritesFlightsStreamUseCase = getAllFavoritesFlightsStreamUseCase,
            )

        airportsFakeDataSource.insertAirportsEntities(airportEntitiesTestData)
    }

    @Test
    fun searchQuery_init_matchResult() = runTest {
        val result = searchScreenViewModel.searchQuery.first()

        assertEquals("", result)
    }

    @Test
    fun searchQuery_set_matchResult() = runTest {
        searchScreenViewModel.setSearchQuery("Set")
        val result = searchScreenViewModel.searchQuery.first()

        assertEquals("Set", result)
    }

    @Test
    fun searchQuery_saveSearchQuery_matchResult() = runTest {
        searchScreenViewModel.setSavedSearchText("Set")

        searchScreenViewModel.getInitSearchQuery()
        val result = searchScreenViewModel.searchQuery.first()

        assertEquals("Set", result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchQuery_find_matchOrder() = runTest {
        initDb()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            searchScreenViewModel.showFlightUiState.collect()
            searchScreenViewModel.searchScreenUiState.collect()
        }

        var searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()
        assertIs<SearchScreenUiState.ShowFavorite>(searchScreenUiState)
        assertEquals(searchScreenUiState.results.count(), 0)

        searchScreenViewModel.setSearchQuery("C")
        searchScreenViewModel.setSavedSearchText("C")
        searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()

        assertIs<SearchScreenUiState.ShowSuggests>(searchScreenUiState)
        val expectOrder = listOf<Long>(9, 8)
        searchScreenUiState.results.forEachIndexed { index, suggestionAirport ->
            assertEquals(expectOrder[index], suggestionAirport.passengers)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun departureId_notIn_matchUiState() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            searchScreenViewModel.showFlightUiState.collect()
        }

        searchScreenViewModel.setDepartureId(100)

        val showFlightUiState = searchScreenViewModel.showFlightUiState.value

        assertIs<ShowFlightUiState.Clear>(showFlightUiState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun departureId_inId_matchResult() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            searchScreenViewModel.showFlightUiState.collect()
            searchScreenViewModel.searchScreenUiState.collect()
        }

        var showFlightUiState = searchScreenViewModel.showFlightUiState.first()
        assertIs<ShowFlightUiState.Clear>(showFlightUiState)

        searchScreenViewModel.setDepartureId(1)

        showFlightUiState = searchScreenViewModel.showFlightUiState.first()
        assertIs<ShowFlightUiState.SelectSuggest>(showFlightUiState)
        assertEquals(1, showFlightUiState.allFlights.count())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoritesEntities_insert_matchResult() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            searchScreenViewModel.showFlightUiState.collect()
            searchScreenViewModel.searchScreenUiState.collect()
        }

        var searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()
        assertIs<SearchScreenUiState.ShowFavorite>(searchScreenUiState)
        assertEquals(searchScreenUiState.results.count(), 0)

        val testFavorite = favoriteEntitiesTestData.first()
        searchScreenViewModel.insertFavorite(
            departureCode = testFavorite.departureCode,
            arriveCode = testFavorite.destinationCode,
        )

        searchScreenViewModel.setSavedSearchText("")
        searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()

        assertIs<SearchScreenUiState.ShowFavorite>(searchScreenUiState)
        assertEquals(searchScreenUiState.results.count(), 1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoriteEntities_insertAndDelete_matchResult() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            searchScreenViewModel.showFlightUiState.collect()
            searchScreenViewModel.searchScreenUiState.collect()
        }

        var searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()
        assertIs<SearchScreenUiState.ShowFavorite>(searchScreenUiState)
        assertEquals(searchScreenUiState.results.count(), 0)

        val testFavorite = favoriteEntitiesTestData.first()
        searchScreenViewModel.insertFavorite(
            departureCode = testFavorite.departureCode,
            arriveCode = testFavorite.destinationCode,
        )

        searchScreenViewModel.setSavedSearchText("")
        searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()

        assertIs<SearchScreenUiState.ShowFavorite>(searchScreenUiState)
        assertEquals(searchScreenUiState.results.count(), 1)

        searchScreenViewModel.deleteFavorite(
            departureCode = testFavorite.departureCode,
            arriveCode = testFavorite.destinationCode,
        )

        searchScreenViewModel.setSavedSearchText("")
        searchScreenUiState = searchScreenViewModel.searchScreenUiState.first()

        assertIs<SearchScreenUiState.ShowFavorite>(searchScreenUiState)
        assertEquals(searchScreenUiState.results.count(), 0)
    }

    private fun getAirportFtsEntities() = airportEntitiesTestData.map {
        it.asFtsEntity()
    }

    private suspend fun initDb() {
        val ftsEntities = getAirportFtsEntities()
        airportsFtsRepository.deleteAndInsertAll(ftsEntities)
    }
}
