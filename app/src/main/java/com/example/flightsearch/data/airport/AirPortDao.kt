package com.example.flightsearch.data.airport

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirPortDao {
    @Query("SELECT * from airport WHERE iata_code = :code")
    fun getAirPortByCode(code: String): Flow<List<AirPort>>

    @Query("SELECT * from airport ORDER BY name ASC")
    fun getAllAirPort(): Flow<List<AirPort>>
}