package com.example.flightsearch.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.FavoriteData
import com.example.flightsearch.data.FavoriteDataRepository

class FlightCardViewModel(
    private val favoriteDataRepository: FavoriteDataRepository
): ViewModel() {

    suspend fun saveData(favoriteData: FavoriteData) {
        favoriteDataRepository.insertFavoriteData(favoriteData)
    }

    suspend fun deleteData(favoriteData: FavoriteData) {
        favoriteDataRepository.deleteFavoriteData(favoriteData)
    }

    suspend fun customDeleteData(departureCode: String, destinationCode: String) {
        favoriteDataRepository.customDeleteData(departureCode,destinationCode)
    }

    fun getAllFavoriteData() = favoriteDataRepository.getAllFavoriteData()

    suspend fun deleteAllFavoriteData() = favoriteDataRepository.deleteAllFavoriteData()

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightCardViewModel(application.container.favoriteDataRepository)
            }
        }
    }
}


data class FavoriteDataUiState(
    val id: Int,
    val departureCode: String,
    val destinationCode: String,
)