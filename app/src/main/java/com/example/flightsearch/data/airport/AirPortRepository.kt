package com.example.flightsearch.data.airport

import kotlinx.coroutines.flow.Flow

interface AirPortRepository {
    fun getAllAirPort(): Flow<List<AirPort>>

    fun getAirPortByCode(code: String): Flow<List<AirPort>>
}