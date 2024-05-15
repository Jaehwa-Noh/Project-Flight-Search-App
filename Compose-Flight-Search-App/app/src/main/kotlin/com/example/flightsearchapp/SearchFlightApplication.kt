package com.example.flightsearchapp

import android.app.Application
import com.example.flightsearchapp.data.AirportsFtsRepository
import com.example.flightsearchapp.data.AirportsRepository
import com.example.flightsearchapp.data.database.asFtsEntity
import com.example.flightsearchapp.di.ApplicationScope
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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