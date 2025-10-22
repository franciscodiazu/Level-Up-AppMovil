package com.example.level_up_appmovil.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.level_up_appmovil.model.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.Period

class AuthViewModel : ViewModel() {

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
        // Calcula los años entre la fecha de nacimiento y hoy
        return Period.between(birthDate, LocalDate.now()).years >= 18
    }

    private fun isDuocEmail(email: String): Boolean {
        // Revisa si el email termina en @duoc.cl o @duocuc.cl
        return email.endsWith("@duoc.cl", ignoreCase = true) ||
                email.endsWith("@duocuc.cl", ignoreCase = true)
    }

    // --- Funciones de Acciones ---

    @RequiresApi(Build.VERSION_CODES.O)
    fun onRegisterClick() {
        _uiState.update { it.copy(isLoading = true) }
        val state = _uiState.value

        // 1. Validar contraseñas
        if (state.regPass != state.regConfirmPass) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Las contraseñas no coinciden") }
            return
        }

        // 2. Validar edad
        if (!isOver18(state.birthDate)) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Debes ser mayor de 18 años") }
            return
        }

        // (Aquí iría la llamada a tu API/Backend para registrar al usuario)

        // 3. Simular éxito y revisar si es Duoc
        val duocMessage = if (isDuocEmail(state.regEmail)) {
            "¡Correo Duoc detectado! Tienes un 20% de descuento."
        } else {
            ""
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                registrationSuccess = true,
                errorMessage = "¡Registro exitoso! $duocMessage" // Usamos errorMessage para mostrar el mensaje de éxito
            )
        }
    }

    fun onLoginClick() {
        _uiState.update { it.copy(isLoading = true) }
        val state = _uiState.value

        // (Aquí iría la llamada a tu API/Backend para hacer login)

        _uiState.update { it.copy(isLoading = false) }
    }
}