package com.example.flightsearchapp.core.testing.fake.di

import com.example.flightsearchapp.core.data.di.CoroutineDispatchersModule
import com.example.flightsearchapp.core.data.di.DispatcherDefault
import com.example.flightsearchapp.core.data.di.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineDispatchersModule::class],
)
object TestDispatchersModule {

    @DispatcherIO
    @Provides
    fun provideCoroutineDispatcherIO(testDispatcher: TestDispatcher): CoroutineDispatcher =
        testDispatcher

    @DispatcherDefault
    @Provides
    fun provideCoroutineDispatcherDefault(testDispatcher: TestDispatcher): CoroutineDispatcher =
        testDispatcher
}
