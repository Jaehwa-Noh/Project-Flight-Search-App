package com.example.flightsearchapp.testing.model.database

import com.example.flightsearchapp.data.database.AirportFtsEntity
import com.example.flightsearchapp.data.database.asFtsEntity
import com.example.flightsearchapp.testing.model.database.entityextension.testAirportEntity

val airportFtsEntitiesTestData: List<AirportFtsEntity> = listOf(
    testAirportEntity(1, "HAM").asFtsEntity(),
    testAirportEntity(2, "CDE").asFtsEntity(),
)
