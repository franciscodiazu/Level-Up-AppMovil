package com.example.level_up_appmovil.model

import java.time.LocalDate

data class AuthUiState(
    // Campos para Login
    val loginEmail: String = "",
    val loginPass: String = "",

    // Campos para Registro
    val regEmail: String = "",
    val regErrorEmail: String? = null,
    val regPass: String = "",
    val regErrorPass: String? = null,
    val regConfirmPass: String = "",
    val regErrorConfirmPass: String? = null,
    val birthDate: LocalDate? = null,
    val regErrorBirthDate: String? = null,

    // Controladores de UI
    val showDatePicker: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null, // Para errores generales (login, etc.)
    val successMessage: String? = null, // Para mostrar en el Toast
    val registrationSuccess: Boolean = false,
    val loginSuccess: Boolean = false
)