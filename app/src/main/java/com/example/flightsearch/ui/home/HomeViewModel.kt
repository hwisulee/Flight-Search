package com.example.flightsearch.ui.home

import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.airport.AirPort
import com.example.flightsearch.data.airport.AirPortRepository
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val airPortRepository: AirPortRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    // HomeScreen
    fun getAllFavorite(): Flow<List<Favorite>> = favoriteRepository.getAllFavorite()

    suspend fun insertFavorite(favorite: Favorite) = favoriteRepository.insertFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = favoriteRepository.deleteFavorite(favorite)

    // ItemSearchScreen
    fun getAllAirPort(): Flow<List<AirPort>> = airPortRepository.getAllAirPort()

    fun getAirPortByCode(code: String): Flow<List<AirPort>> = airPortRepository.getAirPortByCode(code)

    fun updateUserInput(input: String) {
        _uiState.update {
            it.copy(
                userInput = input
            )
        }
    }
}

data class HomeUiState(
    val favoriteList: List<Favorite> = listOf(),
    val searchList: List<AirPort> = listOf(),
    val userInput: String = ""
)