package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FlightData::class, FavoriteData::class], version = 1, exportSchema = false)
abstract class FlightDataDatabase: RoomDatabase() {

    abstract fun flightDataDao(): FlightDataDao
    abstract fun favoriteDataDao(): FavoriteDataDao

    companion object {
        @Volatile
        private var Instance: FlightDataDatabase? = null

        fun getFlightDataDatabase(context: Context): FlightDataDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = FlightDataDatabase::class.java,
                    name = "flight_search_database")
                    .createFromAsset("database/flight_search.db")
                    //.fallbackToDestructiveMigration() // To Save Data, it is unnecessary
                    .build()
                    .also { Instance = it }
                    .also { println("database copied!") }
            }
        }
    }

}