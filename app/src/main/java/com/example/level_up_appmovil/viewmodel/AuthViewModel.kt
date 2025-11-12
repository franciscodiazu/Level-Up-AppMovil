package com.example.level_up_appmovil.viewmodel

import android.app.Application
import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.data.api.model.User
import com.example.level_up_appmovil.data.db.AppDatabase
import com.example.level_up_appmovil.data.repository.UserDataRepository
import com.example.level_up_appmovil.model.AuthUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

@RequiresApi(Build.VERSION_CODES.O)
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDataRepository = UserDataRepository(application)
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // --- Funciones para actualizar el estado ---
    fun onLoginEmailChange(email: String) {
        _uiState.update { it.copy(loginEmail = email, errorMessage = null) }
    }
    fun onLoginPassChange(pass: String) {
        _uiState.update { it.copy(loginPass = pass, errorMessage = null) }
    }
    fun onRegEmailChange(email: String) {
        _uiState.update { it.copy(regEmail = email, regErrorEmail = null) }
    }
    fun onRegPassChange(pass: String) {
        _uiState.update { it.copy(regPass = pass, regErrorPass = null, regErrorConfirmPass = null) }
    }
    fun onRegConfirmPassChange(pass: String) {
        _uiState.update { it.copy(regConfirmPass = pass, regErrorConfirmPass = null) }
    }
    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(birthDate = date, showDatePicker = false, regErrorBirthDate = null) }
    }
    fun showDatePicker(show: Boolean) {
        _uiState.update { it.copy(showDatePicker = show) }
    }
    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }

    // --- Lógica de Negocio (Validaciones) ---
    private fun isOver18(birthDate: LocalDate?): Boolean {
        if (birthDate == null) return false
        return Period.between(birthDate, LocalDate.now()).years >= 18
    }

    private fun isDuocEmail(email: String): Boolean {
        return email.endsWith("@duoc.cl", ignoreCase = true) ||
                email.endsWith("@duocuc.cl", ignoreCase = true)
    }

    // Validación de contraseña (inspirada en Tienda-Huerta)
    private fun validatePassword(pass: String): String? {
        return when {
            pass.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
            !pass.any { it.isDigit() } -> "La contraseña debe tener al menos un número."
            !pass.any { it.isUpperCase() } -> "La contraseña debe tener al menos una mayúscula."
            else -> null
        }
    }

    private fun validateRegistrationForm(): Boolean {
        val state = _uiState.value
        var isValid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(state.regEmail).matches()) {
            _uiState.update { it.copy(regErrorEmail = "Correo no válido") }
            isValid = false
        }
        
        val passError = validatePassword(state.regPass)
        if (passError != null) {
            _uiState.update { it.copy(regErrorPass = passError) }
            isValid = false
        }
        
        if (state.regPass != state.regConfirmPass) {
            _uiState.update { it.copy(regErrorConfirmPass = "Las contraseñas no coinciden") }
            isValid = false
        }
        
        if (!isOver18(state.birthDate)) {
            _uiState.update { it.copy(regErrorBirthDate = "Debes ser mayor de 18 años") }
            isValid = false
        }
        
        return isValid
    }

    // --- Funciones de Acciones (CON SEGURIDAD) ---

    fun onRegisterClick() {
        if (!validateRegistrationForm()) return

        _uiState.update { it.copy(isLoading = true) }
        val state = _uiState.value

        viewModelScope.launch(Dispatchers.IO) {
            // Verificamos si el email ya existe en la BD
            if (userDao.getUserByEmail(state.regEmail) != null) {
                launch(Dispatchers.Main) {
                    _uiState.update { it.copy(isLoading = false, regErrorEmail = "El correo ya está registrado") }
                }
                return@launch
            }

            // Creamos y guardamos el usuario
            val isDuoc = isDuocEmail(state.regEmail)
            val newUser = User(
                email = state.regEmail,
                pass = state.regPass, // RECUERDA: Hashear esto en una app real
                birthDate = state.birthDate!!,
                isDuocMember = isDuoc
            )
            userDao.insertUser(newUser)

            val duocMessage = if (isDuoc) " ¡Correo Duoc detectado! Tienes 20% DCTO." else ""
            launch(Dispatchers.Main) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registrationSuccess = true,
                        successMessage = "¡Registro exitoso!$duocMessage"
                    )
                }
            }
        }
    }

    fun onLoginClick() {
        _uiState.update { it.copy(isLoading = true, loginSuccess = false, errorMessage = null) }
        val state = _uiState.value

        if (state.loginEmail.isBlank() || state.loginPass.isBlank()) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "Correo y contraseña no pueden estar vacíos") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.findUserByEmailAndPassword(state.loginEmail, state.loginPass)

            launch(Dispatchers.Main) {
                if (user == null) {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Correo o contraseña incorrectos") }
                } else {
                    userDataRepository.saveUserSession(user.email, user.isDuocMember)
                    _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                }
            }
        }
    }

    fun consumeLoginSuccess() {
        _uiState.update { it.copy(loginSuccess = false) }
    }
    
    fun consumeRegistrationSuccess() {
        _uiState.update { it.copy(registrationSuccess = false) }
    }
}