package com.example.level_up_appmovil.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.data.api.model.LocalProduct
import com.example.level_up_appmovil.data.db.AppDatabase
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

// Estado de la UI para la pantalla de Admin
data class AdminUiState(
    val products: List<LocalProduct> = emptyList(),
    val id: Int = 0, // 0 significa nuevo producto, >0 significa editar
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val imageUri: String? = null,
    val isEditing: Boolean = false, // Para saber si cambiamos el texto del botón
    val message: String? = null
)

class AdminProductViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        loadLocalProducts()
    }

    private fun loadLocalProducts() {
        viewModelScope.launch {
            // Observamos la lista de productos locales en tiempo real
            userDao.getAllLocalProducts().collectLatest { list ->
                _uiState.update { it.copy(products = list) }
            }
        }
    }

    // Funciones para los campos de texto
    fun onNameChange(text: String) = _uiState.update { it.copy(name = text) }
    fun onCategoryChange(text: String) = _uiState.update { it.copy(category = text) }
    fun onPriceChange(text: String) = _uiState.update { it.copy(price = text) }

    // Función para seleccionar imagen (Reutilizando la lógica que hicimos en Perfil)
    fun onImageSelected(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val internalPath = copyImageToInternalStorage(uri)
            if (internalPath != null) {
                _uiState.update { it.copy(imageUri = internalPath) }
            }
        }
    }

    // Seleccionar un producto de la lista para editarlo
    fun onProductSelected(product: LocalProduct) {
        _uiState.update {
            it.copy(
                id = product.id,
                name = product.name,
                category = product.category,
                price = product.price.toString(),
                imageUri = product.imageUri,
                isEditing = true
            )
        }
    }

    // Limpiar formulario para crear uno nuevo
    fun clearForm() {
        _uiState.update {
            it.copy(id = 0, name = "", category = "", price = "", imageUri = null, isEditing = false)
        }
    }

    // Guardar (Crear o Actualizar)
    fun saveProduct() {
        val state = _uiState.value
        if (state.name.isBlank() || state.price.isBlank()) {
            _uiState.update { it.copy(message = "Faltan datos") }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val product = LocalProduct(
                id = state.id, // Si es 0 crea nuevo, si no, actualiza
                name = state.name,
                category = state.category,
                price = state.price.toIntOrNull() ?: 0,
                imageUri = state.imageUri
            )

            if (state.id == 0) {
                userDao.insertProduct(product)
            } else {
                userDao.updateProduct(product)
            }

            launch(Dispatchers.Main) {
                clearForm() // Limpiamos después de guardar
                _uiState.update { it.copy(message = "Producto Guardado") }
            }
        }
    }

    // Eliminar producto
    fun deleteProduct(product: LocalProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteProduct(product)
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }

    // Copiar imagen (Igual que en Perfil)
    private fun copyImageToInternalStorage(uri: Uri): String? {
        val context = getApplication<Application>()
        return try {
            val fileName = "prod_${UUID.randomUUID()}.jpg"
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream.use { input -> outputStream.use { output -> input.copyTo(output) } }
            file.absolutePath
        } catch (e: Exception) { null }
    }
}