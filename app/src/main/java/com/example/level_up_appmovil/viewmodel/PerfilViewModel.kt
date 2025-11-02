package com.example.level_up_appmovil.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.data.repository.UserDataRepository
import com.example.level_up_appmovil.model.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Usamos AndroidViewModel para poder acceder al 'Context'
class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    // Instanciamos el repositorio pasándole el contexto
    private val userDataRepository = UserDataRepository(application)

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        // Observamos el email del usuario
        viewModelScope.launch {
            userDataRepository.userEmail.collect { email ->
                _uiState.update { it.copy(email = email ?: "No disponible") }
            }
        }

        // Observamos si es miembro Duoc
        viewModelScope.launch {
            userDataRepository.isDuocMember.collect { isDuoc ->
                _uiState.update { it.copy(isDuocMember = isDuoc) }
            }
        }
    }

    fun onLogoutClick() {
        // Borramos la sesión guardada en DataStore
        viewModelScope.launch {
            userDataRepository.clearUserSession()
        }
    }
}