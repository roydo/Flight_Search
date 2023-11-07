package com.example.flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.AppContainer

class AppDataContainer(private val context: Context): AppContainer {
    override val flightDataRepository: FlightDataRepository by lazy {
        FlightDataRepository(FlightDataDatabase.getFlightDataDatabase(context).flightDataDao())
    }
    override val favoriteDataRepository: FavoriteDataRepository by lazy {
        FavoriteDataRepository(FlightDataDatabase.getFlightDataDatabase(context).favoriteDataDao())
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "latest_user_search_word"
    )
    override val userRepository: UserRepository = UserRepository(context.dataStore)

}