package com.example.flightsearch.data.airport

import kotlinx.coroutines.flow.Flow

class OfflineAirPortRepository(private val airPortDao: AirPortDao): AirPortRepository {
    override fun getAllAirPort(): Flow<List<AirPort>> = airPortDao.getAllAirPort()

    override fun getAirPortByCode(code: String): Flow<List<AirPort>> = airPortDao.getAirPortByCode(code)
}