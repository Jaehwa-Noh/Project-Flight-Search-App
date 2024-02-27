package com.example.flightsearchapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SearchTextRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCH_TEXT = stringPreferencesKey("search_text")
    }

    val savedSearchText: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SEARCH_TEXT] ?: ""
        }

    suspend fun saveSearchText(searchText: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_TEXT] = searchText
        }
    }
}