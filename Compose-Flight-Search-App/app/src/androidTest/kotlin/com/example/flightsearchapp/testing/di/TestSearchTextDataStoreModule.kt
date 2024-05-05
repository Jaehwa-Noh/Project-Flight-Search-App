package com.example.flightsearchapp.testing.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.flightsearchapp.di.DataStoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.test.TestScope
import javax.inject.Singleton

private const val TEST_SEARCH_TEXT = "test_search_text"

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class],
)
object TestSearchTextDataStoreModule {
    @Provides
    @Singleton
    fun provideTestSearchTextDataStore(
        @ApplicationContext applicationContext: Context,
        testScope: TestScope,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = {
            applicationContext.preferencesDataStoreFile(TEST_SEARCH_TEXT)
        }
    )
}
