package com.gitan.mystoryapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gitan.mystoryapp.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[NAME_KEY] ?: "",
                preferences[USER_ID_KEY] ?:"",
                preferences[TOKEN_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] =  user.name
            preferences[USER_ID_KEY] = user.userId
            preferences[TOKEN_KEY] = user.token
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[USER_ID_KEY] = ""
            preferences[TOKEN_KEY] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}