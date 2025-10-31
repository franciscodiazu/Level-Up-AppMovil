package com.example.level_up_appmovil.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Definir la instancia de DataStore a nivel de archivo
// Esto asegura que solo haya una instancia en toda la app.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

// 2. Definir las claves para acceder a los datos
private object PreferencesKeys {
    val USER_EMAIL = stringPreferencesKey("user_email")
    val IS_DUOC_MEMBER = booleanPreferencesKey("is_duoc_member")
}

// 3. Crear el repositorio para manejar las operaciones de datos
class UserDataRepository(private val context: Context) {

    // Flujo para saber si hay un usuario logueado (si su email está guardado)
    val isUserLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL]?.isNotEmpty() ?: false
        }

    // Flujo para obtener el email del usuario actual
    val userEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL]
        }

    // Flujo para saber si el usuario tiene el descuento Duoc
    val isDuocMember: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_DUOC_MEMBER] ?: false
        }

    // Función para guardar la sesión del usuario
    suspend fun saveUserSession(email: String, isDuocMember: Boolean) {
        context.dataStore.edit {
            preferences ->
            preferences[PreferencesKeys.USER_EMAIL] = email
            preferences[PreferencesKeys.IS_DUOC_MEMBER] = isDuocMember
        }
    }

    // Función para cerrar la sesión del usuario
    suspend fun clearUserSession() {
        context.dataStore.edit {
            preferences ->
            preferences.clear()
        }
    }
}
