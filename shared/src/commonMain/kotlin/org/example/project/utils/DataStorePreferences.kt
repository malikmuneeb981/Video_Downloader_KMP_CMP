package org.example.project.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.commons.DataStorePreferencesModule


class DataStorePreferences(dataStorePreferencesModule: DataStorePreferencesModule) {

    val dataStore = dataStorePreferencesModule.createDataStore

    // Save a String value
    suspend fun saveString(key: String, value: String) {
        val dataKey = stringPreferencesKey(key)
       dataStore.edit { preferences ->
            preferences[dataKey] = value
        }
    }

    // Save a Boolean value
    suspend fun saveBoolean(key: String, value: Boolean) {
        val dataKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataKey] = value
        }
    }

    // Save a Int value
    suspend fun saveInt(key: String, value: Int) {
        val dataKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataKey] = value
        }
    }

    // Get a String value as Flow
    fun getString(key: String): Flow<String?> {
        val dataKey = stringPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[dataKey]
        }
    }

    // Get a Boolean value as Flow
    fun getBoolean(key: String): Flow<Boolean?> {
        val dataKey = booleanPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[dataKey]
        }
    }

    // Get a Int value as Flow
    fun getInt(key: String): Flow<Int?> {
        val dataKey = intPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[dataKey]
        }
    }
}
