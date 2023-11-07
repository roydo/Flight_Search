package com.example.flightsearch.ui.screen

import android.text.Spannable.Factory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiData (
    val isClicked: Boolean = false,
    val userChoiceFlightId: Int = 0,
    //val lastUserSearchWord: String = ""
)

data class LastUserSearchWordUiState (
    val lastUserSearchWord: String = ""
)

class HomeViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    /*private val lastUserSearchWordState: StateFlow<HomeUiData>
    = userRepository.latestUserSearchWord.map {
        HomeUiData(lastUserSearchWord = it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiData()
        )

     */

    private val _homeUiState = MutableStateFlow(HomeUiData())
    val homeUiState = _homeUiState.asStateFlow()

    private val _lastUserSearchWordUiState = MutableStateFlow(LastUserSearchWordUiState())
    val lastUserSearchWordUiState: StateFlow<LastUserSearchWordUiState> = userRepository.latestUserSearchWord.map {
        println("saved data: $it")
        LastUserSearchWordUiState(lastUserSearchWord = it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LastUserSearchWordUiState()
        )

    fun updateIsClicked(isClicked: Boolean) {
        _homeUiState.update { currentState ->
            currentState.copy(
                isClicked = isClicked
            )
        }
    }

    fun updateUserChoiceFlightId(id: Int) {
        _homeUiState.update { currentState ->
            currentState.copy(
                userChoiceFlightId = id
            )
        }
    }

    // to save user search words
    fun saveLastUserSearchWord(lastUserSearchWord: String) {
        viewModelScope.launch {
            userRepository.saveLatestWord(lastUserSearchWord)
            println("DataStore Saved!")
            println(lastUserSearchWord)
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                HomeViewModel(application.container.userRepository)
            }
        }
    }

}