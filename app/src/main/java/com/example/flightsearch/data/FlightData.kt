package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class FlightData(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "passengers")
    val passengers: Int,
)

/*
*  To save user's favorite flight data
*  */
@Entity(tableName = "favorite")
data class FavoriteData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)