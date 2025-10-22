package com.example.level_up_appmovil.model // Revisa que tu package sea el correcto

import java.time.LocalDate

data class AuthUiState(
    // Campos para Login
    val loginEmail: String = "",
    val loginPass: String = "",

    // Campos para Registro
    val regEmail: String = "",
    val regPass: String = "",
    val regConfirmPass: String = "",
    val birthDate: LocalDate? = null,

    // Controladores de UI
    val showDatePicker: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registrationSuccess: Boolean = false
)