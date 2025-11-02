package com.example.level_up_appmovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.level_up_appmovil.data.api.repository.ProductRepository
import com.example.level_up_appmovil.model.CatalogoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CatalogoViewModel : ViewModel() {

    private val productRepository = ProductRepository()

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        // Obtenemos los productos de muestra del repositorio
        val sampleProducts = productRepository.getSampleProducts()
        _uiState.update { it.copy(products = sampleProducts) }
    }
}