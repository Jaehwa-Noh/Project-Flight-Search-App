package com.example.flightsearchapp

import android.app.Application
import com.example.flightsearchapp.core.data.di.ApplicationScope
import com.example.flightsearchapp.core.data.model.asFtsEntity
import com.example.flightsearchapp.core.data.repository.AirportsFtsRepository
import com.example.flightsearchapp.core.data.repository.AirportsRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SearchFlightApplication : Application() {
    @Inject
    lateinit var airportsRepository: AirportsRepository

    @Inject
    lateinit var airportsFtsRepository: AirportsFtsRepository

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            airportsFtsRepository.deleteAndInsertAll(
                airportsRepository.getAllAirportsEntities().map { airportEntity ->
                    airportEntity.asFtsEntity()
                },
            )
        }
    }
}
