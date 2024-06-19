package com.example.flightsearchapp.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

interface SearchTextRepository {
    val savedSearchText: Flow<String>
    suspend fun saveSearchText(searchText: String)
}

@Singleton
class InDiskSearchTextRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SearchTextRepository {
    private companion object {
        val SEARCH_TEXT = stringPreferencesKey("search_text")
    }

    override val savedSearchText: Flow<String> = dataStore.data
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

    override suspend fun saveSearchText(searchText: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_TEXT] = searchText
        }
    }
}
