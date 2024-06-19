package com.example.flightsearchapp.core.testing.fake.data.database

import com.example.flightsearchapp.core.data.model.asFtsEntity
import com.example.flightsearchapp.core.database.model.AirportFtsEntity

val airportFtsEntitiesTestData: List<AirportFtsEntity> = listOf(
    testAirportEntity(1, "HAM").asFtsEntity(),
    testAirportEntity(2, "CDE").asFtsEntity(),
)
