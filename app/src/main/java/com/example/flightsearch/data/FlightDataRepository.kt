package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class FlightDataRepository(private val flightDataDao: FlightDataDao) {
    fun getTestData(): Flow<FlightData> = flightDataDao.getTestData()

    fun getAutoComplete(searchWord: String): Flow<List<FlightData>>
    = flightDataDao.autoComplete(searchWord)

    fun getAllDataExpectItself(id: Int): Flow<List<FlightData>>
    = flightDataDao.getAllDataExpectItself(id)

    fun getUserChoiceData(id: Int): Flow<FlightData>
    = flightDataDao.getUserChoiceData(id)

    suspend fun getAirportNameFromIataCode(iataCode: String): String
    = flightDataDao.getAirportNameFromIataCode(iataCode = iataCode)
}

class FavoriteDataRepository(private val favoriteDataDao: FavoriteDataDao) {
    suspend fun insertFavoriteData(favoriteData: FavoriteData) =
        favoriteDataDao.insertFavoriteData(favoriteData)

    suspend fun deleteFavoriteData(favoriteData: FavoriteData) =
        favoriteDataDao.deleteFavoriteData(favoriteData)

    suspend fun customDeleteData(departureCode: String, destinationCode: String, ) =
        favoriteDataDao.customDeleteData(departureCode = departureCode,destinationCode = destinationCode)

    fun getAllFavoriteData(): Flow<List<FavoriteData>> =
        favoriteDataDao.getAllFavoriteData()

    suspend fun deleteAllFavoriteData() = favoriteDataDao.deleteAllFavoriteData()
}