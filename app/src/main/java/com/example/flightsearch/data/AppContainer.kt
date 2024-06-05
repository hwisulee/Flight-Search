package com.example.flightsearch.data

import android.content.Context
import com.example.flightsearch.data.airport.AirPortRepository
import com.example.flightsearch.data.airport.OfflineAirPortRepository
import com.example.flightsearch.data.favorite.FavoriteRepository
import com.example.flightsearch.data.favorite.OfflineFavoriteRepository

interface AppContainer {
    val airPortRepository: AirPortRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val airPortRepository: AirPortRepository by lazy {
        OfflineAirPortRepository(AppDatabase.getDatabase(context).airPortDao())
    }

    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(AppDatabase.getDatabase(context).favoriteDao())
    }
}