package com.example.flightsearch.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.FavoriteDataRepository

class FavoriteListViewModel(): ViewModel() {


}

data class FavoriteListUiState (
    val departureAirportName: String,
    val destinationAirportName: String,
)
