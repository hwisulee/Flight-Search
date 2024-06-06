package com.example.flightsearch.data.airport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class AirPort(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "iata_code")
    var iataCode: String,
    @ColumnInfo(name = "passengers")
    val passengers: Int
)
