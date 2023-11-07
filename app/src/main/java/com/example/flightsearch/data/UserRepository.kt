package com.example.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val LATEST_USER_SEARCH_WORD = stringPreferencesKey("latest_user_search_word")

        // value to catch error
        const val TAG = "UserPreferencesRepo"
    }

    // write data to DataStore
    suspend fun saveLatestWord(latestUserWord: String) {
        dataStore.edit { preferences ->
            preferences[LATEST_USER_SEARCH_WORD] = latestUserWord
        }
    }

    // read data form DataStore
    val latestUserSearchWord: Flow<String> = dataStore.data
    // catch error
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
            } else {
                throw it
            }
        }
    // expand data
        .map { preferences ->
            preferences[LATEST_USER_SEARCH_WORD] ?: "initial" // to set initial value
        }
}