package com.example.level_up_appmovil.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.data.repository.UserDataRepository
import com.example.level_up_appmovil.model.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

// 1. Cambiamos a AndroidViewModel para tener el 'context'
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    // 2. Instanciamos el repositorio de datos del usuario
    private val userDataRepository = UserDataRepository(application)

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // --- Funciones para actualizar el estado ---
    fun onLoginEmailChange(email: String) {
        _uiState.update { it.copy(loginEmail = email) }
    }
    fun onLoginPassChange(pass: String) {
        _uiState.update { it.copy(loginPass = pass) }
    }
    fun onRegEmailChange(email: String) {
        _uiState.update { it.copy(regEmail = email) }
    }
    fun onRegPassChange(pass: String) {
        _uiState.update { it.copy(regPass = pass) }
    }
    fun onRegConfirmPassChange(pass: String) {
        _uiState.update { it.copy(regConfirmPass = pass) }
    }
    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(birthDate = date, showDatePicker = false) }
    }
    fun showDatePicker(show: Boolean) {
        _uiState.update { it.copy(showDatePicker = show) }
    }
    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // --- Lógica de Negocio (Requisitos del PDF) ---

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isOver18(birthDate: LocalDate?): Boolean {
        if (birthDate == null) return false
        return Period.between(birthDate, LocalDate.now()).years >= 18
    }

    internal fun isDuocEmail(email: String): Boolean {
        return email.endsWith("@duoc.cl", ignoreCase = true) ||
                email.endsWith("@duocuc.cl", ignoreCase = true)
    }

    // --- Funciones de Acciones ---

    @RequiresApi(Build.VERSION_CODES.O)
    fun onRegisterClick() {
        // ... (Tu función de registro está perfecta, no la toques)
        _uiState.update { it.copy(isLoading = true) }
        val state = _uiState.value

        if (state.regPass != state.regConfirmPass) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Las contraseñas no coinciden") }
            return
        }

        if (!isOver18(state.birthDate)) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Debes ser mayor de 18 años") }
            return
        }

        val duocMessage = if (isDuocEmail(state.regEmail)) {
            "¡Correo Duoc detectado! Tienes un 20% de descuento."
        } else {
            ""
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                registrationSuccess = true,
                errorMessage = "¡Registro exitoso! $duocMessage"
            )
        }
    }

    // 3. ¡MODIFICACION DEL LOGINCLICK!
    fun onLoginClick() {
        _uiState.update { it.copy(isLoading = true, loginSuccess = false) }
        val state = _uiState.value

        // Lanzamos una corutina para guardar los datos
        viewModelScope.launch {
            val isDuoc = isDuocEmail(state.loginEmail)
            // Guardamos la sesión en DataStore
            userDataRepository.saveUserSession(state.loginEmail, isDuoc)

            // Actualizamos el estado para avisar a la UI que el login fue exitoso
            _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
        }
    }

    // 4. Añadimos una función para 'consumir' el evento de login
    fun consumeLoginSuccess() {
        _uiState.update { it.copy(loginSuccess = false) }
    }
}