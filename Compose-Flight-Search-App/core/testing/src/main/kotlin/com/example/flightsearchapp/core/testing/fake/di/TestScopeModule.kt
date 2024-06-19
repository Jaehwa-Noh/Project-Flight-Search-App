package com.example.flightsearchapp.core.testing.fake.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@InstallIn(SingletonComponent::class)
object TestScopeModule {
    @Provides
    @Singleton
    fun provideTestScheduler(): TestCoroutineScheduler = TestCoroutineScheduler()

    @Provides
    @Singleton
    fun provideTestDispatcher(testCoroutineScheduler: TestCoroutineScheduler): TestDispatcher =
        UnconfinedTestDispatcher(
            testCoroutineScheduler,
        )

    @Provides
    @Singleton
    fun provideTestScope(dispatcher: TestDispatcher): TestScope = TestScope(dispatcher)
}
