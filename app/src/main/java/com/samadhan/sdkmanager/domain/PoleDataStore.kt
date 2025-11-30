package com.samadhan.sdkmanager.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserCredentialsDataStore(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("credentials_prefs")

    companion object {
        val USER_CRED_KEY = stringPreferencesKey("user_credentials")
    }

    suspend fun saveUser(username: String, password: String) {
        context.dataStore.edit { prefs ->
            val raw = prefs[USER_CRED_KEY] ?: ""
            val map = raw.lines()
                .filter { it.isNotBlank() }
                .associate {
                    val (user, pass) = it.split(":")
                    user to pass
                }.toMutableMap()
            if (map.containsKey(username) && map[username] == password) {
                return@edit
            }
            map[username] = password
            prefs[USER_CRED_KEY] = map.entries.joinToString("\n") { "${it.key}:${it.value}" }
        }
    }


    val getUsers: Flow<Map<String, String>> = context.dataStore.data.map { prefs ->
        val raw = prefs[USER_CRED_KEY] ?: ""
        raw.lines().filter { it.isNotBlank() }.associate {
            val (user, pass) = it.split(":")
            user to pass
        }
    }
}


