package com.example.level_up_appmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up_appmovil.data.api.repository.ProductRepository
import com.example.level_up_appmovil.model.CatalogoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogoViewModel : ViewModel() {

    private val productRepository = ProductRepository()

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            // Llamamos al repositorio en segundo plano
            val productsList = productRepository.getProducts()
            _uiState.update { it.copy(products = productsList) }
        }
    }
}