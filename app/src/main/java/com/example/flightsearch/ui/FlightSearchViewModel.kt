package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.FlightData
import com.example.flightsearch.data.FlightDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FlightSearchViewModel(
    private val flightDataRepository: FlightDataRepository
): ViewModel() {
    // test data get
    fun getTestData(): Flow<FlightData> = flightDataRepository.getTestData()

    // get auto complete
    fun getAutoComplete(searchWord: String): Flow<List<FlightData>>
    = flightDataRepository.getAutoComplete(searchWord)
    /*{
        if(searchWord.isNotBlank()) {
            return flightDataRepository.getAutoComplete(searchWord)
        } else {
            return flowOf(listOf(FlightData(
                id = 1,"","",0
            )))
            //flowOf(emptyList())
        }
    }*/
    //= if()flightDataRepository.getAutoComplete(searchWord)


    // get all data except itself
    fun getAllDataExpectItself(id: Int): Flow<List<FlightData>>
    = flightDataRepository.getAllDataExpectItself(id)

    // get user choose data
    fun getUserChoiceData(id: Int): Flow<FlightData>
    = flightDataRepository.getUserChoiceData(id)

    // search word state
    /*private val _searchWordState = MutableStateFlow(SearchWordState())
    val searchUiState: StateFlow<SearchWordState> = _searchWordState.asStateFlow()

    fun updateSearchWord(searchWord: String) {
        _searchWordState.update {  currentState ->
            currentState.copy(
                searchWord = searchWord
            )
        }
    }

    val candidateDataStateFlow: StateFlow<SearchUiState> = flightDataRepository.getAutoComplete(searchUiState.toString()).map { SearchUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SearchUiState()
        )
     */

    // get favorite airport name from iata code
    suspend fun getAirportNameFromIataCode(iataCode: String): String
    = flightDataRepository.getAirportNameFromIataCode(iataCode)

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(application.container.flightDataRepository)
            }
        }
    }
}

/*
data class SearchUiState(
    val flightDataList: List<FlightData> = listOf()
)

data class SearchWordState(
    val searchWord: String = ""
)
 */