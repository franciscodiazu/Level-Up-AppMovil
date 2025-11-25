package com.example.level_up_appmovil.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.data.db.AppDatabase
import com.example.level_up_appmovil.data.repository.UserDataRepository
import com.example.level_up_appmovil.model.PerfilUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val userDataRepository = UserDataRepository(application)
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // 1. Obtenemos el email de la sesión
            userDataRepository.userEmail.collectLatest { email ->
                if (email != null) {
                    // 2. Cargamos los datos desde la BD
                    val user = userDao.getUserByEmail(email)
                    if (user != null) {
                        _uiState.update {
                            it.copy(
                                email = user.email,
                                name = user.name,
                                phone = user.phone,
                                photoUri = user.photoUri, // Aquí cargará la ruta local
                                isDuocMember = user.isDuocMember,
                                isLoading = false
                            )
                        }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onPhoneChange(newPhone: String) {
        _uiState.update { it.copy(phone = newPhone) }
    }

    // --- CAMBIO PRINCIPAL AQUÍ ---
    fun onPhotoSelected(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            // Copiamos la imagen a la memoria interna de la app
            val internalPath = copyImageToInternalStorage(uri)

            if (internalPath != null) {
                // Actualizamos el estado con la NUEVA ruta interna
                _uiState.update { it.copy(photoUri = internalPath) }
            }
        }
    }

    // Función auxiliar para copiar el archivo
    private fun copyImageToInternalStorage(uri: Uri): String? {
        val context = getApplication<Application>()
        return try {
            // Crear un nombre único para el archivo
            val fileName = "profile_${UUID.randomUUID()}.jpg"

            // Abrir flujo de entrada desde la galería
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            // Crear archivo en el directorio de la app
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            // Copiar datos
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            // Devolver la ruta absoluta del archivo creado
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _uiState.value
            val user = userDao.getUserByEmail(currentState.email)

            if (user != null) {
                val updatedUser = user.copy(
                    name = currentState.name,
                    phone = currentState.phone,
                    photoUri = currentState.photoUri // Guardamos la ruta del archivo local
                )

                userDao.updateUser(updatedUser)

                launch(Dispatchers.Main) {
                    _uiState.update { it.copy(successMessage = "Perfil actualizado correctamente") }
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(successMessage = null) }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            userDataRepository.clearUserSession()
        }
    }
}