package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.airport.AirPort
import com.example.flightsearch.data.airport.AirPortDao
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteDao

@Database(entities = [AirPort::class, Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun airPortDao(): AirPortDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "flight_search_database")
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}