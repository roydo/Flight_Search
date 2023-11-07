package com.example.flightsearch.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.FavoriteData
import kotlinx.coroutines.launch

@Composable
fun FlightCard(
    userChoiceAirportName: String,
    userChoiceAirportIataCode: String,
    arriveAirportName: String,
    arriveAirportIataCode: String,
    modifier: Modifier = Modifier,
    flightCardViewModel: FlightCardViewModel
) {
    // create coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // toggle state of favorite
    var isFavorite by remember {
        mutableStateOf(false)
    }

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
                        if(!isFavorite) {
                            isFavorite = true
                            flightCardViewModel.saveData(
                                favoriteData = FavoriteData(
                                    departureCode = userChoiceAirportIataCode,
                                    destinationCode = arriveAirportIataCode
                                )
                            )
                        } else {
                            isFavorite = false
                            flightCardViewModel.customDeleteData(
                                departureCode = userChoiceAirportIataCode,
                                destinationCode = arriveAirportIataCode
                            )
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "favorite",
                    tint =
                        if (!isFavorite) Color.LightGray else MaterialTheme.colorScheme.primary
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

@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    FlightCard(
        userChoiceAirportName = "test international airport",
        userChoiceAirportIataCode = "ABC",
        arriveAirportName = "arrived testing airport",
        arriveAirportIataCode = "EDF",
        flightCardViewModel = viewModel()
    )
}