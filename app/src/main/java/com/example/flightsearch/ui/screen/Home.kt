package com.example.flightsearch.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.ui.FlightSearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.FlightData

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: FlightSearchViewModel = viewModel(factory =
    FlightSearchViewModel.factory)
) {
    // To wake up database(ROOM)
    val testData by viewModel.getTestData().collectAsState(null)
    Column {
        SearchFlight(viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFlight(
    viewModel: FlightSearchViewModel,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    flightCardViewModel: FlightCardViewModel = viewModel(factory = FlightCardViewModel.factory),
    modifier: Modifier = Modifier
) {

    /*
    * Home UI State
    * */
    val homeUiState by homeViewModel.homeUiState.collectAsState()

    val lastUserSearchWordUiState by homeViewModel.lastUserSearchWordUiState.collectAsState()

    val previousLastUserSearchWord = lastUserSearchWordUiState.lastUserSearchWord

    // User input texts
    var searchText by remember {
        mutableStateOf(previousLastUserSearchWord)
    }

    var oneTimeCount by remember {
        mutableStateOf(0)
    }

    if(previousLastUserSearchWord.isNotBlank() && oneTimeCount == 0) {
        searchText = previousLastUserSearchWord
        oneTimeCount++
    }

    /*
    * Database State setting
    * */
    val searchUiState by viewModel.getAutoComplete(searchText).collectAsState(initial = emptyList())

    val searchResultUiState by viewModel.getAllDataExpectItself(homeUiState.userChoiceFlightId).collectAsState(initial = emptyList())
    
    val userChoiceData by viewModel.getUserChoiceData(homeUiState.userChoiceFlightId).collectAsState(initial = null)

    Column(
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            label = {
                    Text(text = "Airport Name or IATA Code")
            },
            value = searchText,
            onValueChange = {
                homeViewModel.updateIsClicked(false)
                searchText = it
                homeViewModel.saveLastUserSearchWord(it)
            },
            shape = RoundedCornerShape(50),
            modifier = modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = lastUserSearchWordUiState.lastUserSearchWord)
            Box {
                if(!homeUiState.isClicked) {
                    AutoComplete(searchUiState = searchUiState, homeViewModel = homeViewModel)
                }
                if (homeUiState.isClicked) {
                    FlightList(
                        userChoiceData = userChoiceData,
                        searchResultUiState = searchResultUiState,
                        homeViewModel = homeViewModel,
                        flightCardViewModel = flightCardViewModel
                    )
                }
        }
        if(searchText.isBlank()) {
            FavoriteList(
                viewModel = flightCardViewModel,
                flightSearchViewModel = viewModel
            )
        }
    }
}

@Composable
fun AutoComplete(
    searchUiState: List<FlightData>,
    homeViewModel: HomeViewModel
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .animateContentSize()
            .padding(horizontal = 16.dp)
    ) {
        items(searchUiState.size) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clickable {
                        homeViewModel.updateIsClicked(true)
                        homeViewModel.updateUserChoiceFlightId(searchUiState[it].id)
                    }
            ) {
                Text(
                    text = searchUiState[it].iataCode,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = searchUiState[it].name
                )
            }
        }
    }
}

@Composable
fun FlightList(
    userChoiceData: FlightData?,
    searchResultUiState: List<FlightData>,
    homeViewModel: HomeViewModel,
    flightCardViewModel: FlightCardViewModel
) {
    Column {
        Text(text = userChoiceData?.name ?: "test")
        Text(text = "${searchResultUiState.size}")
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal =  16.dp)
    ) {
        items(searchResultUiState.size) {
            Column {
                /*
                Text(text = userChoiceData?.name ?: "test")
                Text(text = searchResultUiState[it].name)
                 */
                FlightCard(
                    userChoiceAirportName = userChoiceData?.name ?: "test",
                    userChoiceAirportIataCode = userChoiceData?.iataCode ?: "Unknown",
                    arriveAirportName = searchResultUiState[it].name,
                    arriveAirportIataCode = searchResultUiState[it].iataCode,
                    flightCardViewModel = flightCardViewModel
                )
            }
        }
    }
}

