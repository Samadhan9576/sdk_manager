package com.samadhan.sdkmanager.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.credentialsDataStore by preferencesDataStore("credentials_prefs")

class UserCredentialsDataStore(private val context: Context) {

    companion object {
        val USER_CRED_KEY = stringPreferencesKey("user_credentials")
    }

    suspend fun saveUser(username: String, password: String) {
        context.credentialsDataStore.edit { prefs ->
            val raw = prefs[USER_CRED_KEY] ?: ""
            val map = raw.lines()
                .filter { it.isNotBlank() }
                .associate {
                    val (user, pass) = it.split(":")
                    user to pass
                }.toMutableMap()

            if (map[username] == password) return@edit

            map[username] = password
            prefs[USER_CRED_KEY] =
                map.entries.joinToString("\n") { "${it.key}:${it.value}" }
        }
    }

    val getUsers: Flow<Map<String, String>> =
        context.credentialsDataStore.data.map { prefs ->
            val raw = prefs[USER_CRED_KEY] ?: ""
            raw.lines().filter { it.isNotBlank() }.associate {
                val (user, pass) = it.split(":")
                user to pass
            }
        }
}
