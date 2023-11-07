package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDataDao {
    @Query("SELECT * from airport WHERE id = 1")
    fun getTestData(): Flow<FlightData>

    // Auto Complete
    @Query(
        """
            SELECT * from airport 
            WHERE :searchWord != '' AND
            (name LIKE '%' || :searchWord || '%' OR iata_code LIKE '%' || :searchWord || '%')
        """)
    fun autoComplete(searchWord: String): Flow<List<FlightData>>

    @Query(
        """
            SELECT * from airport WHERE NOT id = :id
        """)
    fun getAllDataExpectItself(id: Int): Flow<List<FlightData>>

    @Query(
        """
            SELECT * from airport WHERE id = :id
        """)
    fun getUserChoiceData(id: Int): Flow<FlightData>

    // get airport name from iata_code
    @Query(
        """
            SELECT name from airport WHERE iata_code = :iataCode
        """)
    suspend fun getAirportNameFromIataCode(iataCode: String): String
}

@Dao
interface FavoriteDataDao {
    // create new favorite data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteData(favoriteData: FavoriteData)

    // delete favorite data
    @Delete
    suspend fun deleteFavoriteData(favoriteData: FavoriteData)

    // custom delete
    @Query("DELETE from favorite WHERE (departure_code = :departureCode AND destination_code = :destinationCode)")
    suspend fun customDeleteData(departureCode: String, destinationCode: String, )

    // get all data
    @Query("SELECT * from favorite")
    fun getAllFavoriteData(): Flow<List<FavoriteData>>

    // all delete for developer
    @Query("DELETE from favorite")
    suspend fun deleteAllFavoriteData()

}