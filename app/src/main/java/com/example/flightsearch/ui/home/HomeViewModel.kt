package com.example.flightsearch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.airport.AirPort
import com.example.flightsearch.data.airport.AirPortRepository
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val airPortRepository: AirPortRepository
): ViewModel() {
    val uiState: StateFlow<HomeUiState> = favoriteRepository.getAllFavorite().map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState()
        )

    // HomeScreen
    fun getAllFavorite(): Flow<List<Favorite>> = favoriteRepository.getAllFavorite()

    suspend fun insertFavorite(favorite: Favorite) = favoriteRepository.insertFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = favoriteRepository.deleteFavorite(favorite)

    // ItemSearchScreen
    fun getAllAirPort(): Flow<List<AirPort>> = airPortRepository.getAllAirPort()

    fun getAirPortByCode(code: String): Flow<List<AirPort>> = airPortRepository.getAirPortByCode(code)
}

data class HomeUiState(
    val favoriteList: List<Favorite> = listOf(),
    val searchList: List<AirPort> = listOf(),
    val airPortList: List<AirPort> = listOf()
)