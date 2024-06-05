package com.example.flightsearch.data.favorite

import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(private val favoriteDao: FavoriteDao): FavoriteRepository {
    override suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)

    override fun getAllFavorite(): Flow<List<Favorite>> = favoriteDao.getAllFavorite()
}