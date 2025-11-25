package com.example.level_up_appmovil.model

data class PerfilUiState(
    val email: String = "",
    val name: String = "",
    val phone: String = "",
    val photoUri: String? = null,
    val isDuocMember: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null
)