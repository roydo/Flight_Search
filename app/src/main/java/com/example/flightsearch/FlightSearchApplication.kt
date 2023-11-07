package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.AppDataContainer
import com.example.flightsearch.data.FavoriteDataRepository
import com.example.flightsearch.data.FlightDataDatabase
import com.example.flightsearch.data.FlightDataRepository
import com.example.flightsearch.data.UserRepository

interface AppContainer {
    val flightDataRepository: FlightDataRepository
    val favoriteDataRepository: FavoriteDataRepository
    val userRepository: UserRepository
}

class FlightSearchApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}