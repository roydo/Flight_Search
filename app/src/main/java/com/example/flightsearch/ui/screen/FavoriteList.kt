package com.example.flightsearch.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.FavoriteData
import com.example.flightsearch.ui.FlightSearchViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteList(
    viewModel: FlightCardViewModel,
    flightSearchViewModel: FlightSearchViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    
    val favoriteUiState by viewModel.getAllFavoriteData().collectAsState(initial = emptyList())

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        modifier = Modifier
            .animateContentSize()
    ) {
        items(favoriteUiState.size) {
            // departure airport name
            var departureAirportName by remember {
                mutableStateOf("")
            }
            // destination airport name
            var destinationAirportName by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = Unit) {
                departureAirportName = flightSearchViewModel.getAirportNameFromIataCode(
                    favoriteUiState[it].departureCode
                    )
                destinationAirportName = flightSearchViewModel.getAirportNameFromIataCode(
                    favoriteUiState[it].destinationCode
                )
            }
            FavoriteCard(
                userChoiceAirportName = departureAirportName,
                userChoiceAirportIataCode = favoriteUiState[it].departureCode,
                arriveAirportName = destinationAirportName,
                arriveAirportIataCode = favoriteUiState[it].destinationCode,
                flightCardViewModel = viewModel
            )
        }
    }
    Button(
        onClick = {
            coroutineScope.launch {
                viewModel.deleteAllFavoriteData()
            } 
        }
    ) {
        Text(text = "ALL DELETE")
    }
}

@Composable
fun FavoriteCard(
    userChoiceAirportName: String,
    userChoiceAirportIataCode: String,
    arriveAirportName: String,
    arriveAirportIataCode: String,
    modifier: Modifier = Modifier,
    flightCardViewModel: FlightCardViewModel
) {
    // create coroutine scope
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .padding(16.dp)
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        flightCardViewModel.customDeleteData(
                            departureCode = userChoiceAirportIataCode,
                            destinationCode = arriveAirportIataCode
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                )
            }

            Column(
                //modifier = modifier
            ) {
                Text(text = "DEPART")
                Text(
                    text = userChoiceAirportIataCode,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = userChoiceAirportName, fontSize = 12.sp)
                Divider(modifier = Modifier.padding(8.dp))
                Text(text = "ARRIVE")
                Text(
                    text = arriveAirportIataCode,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = arriveAirportName, fontSize = 12.sp)
            }
        }
    }
}