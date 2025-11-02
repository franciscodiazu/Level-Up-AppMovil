package com.example.level_up_appmovil.model

import com.example.level_up_appmovil.data.api.model.Product

data class CatalogoUiState(
    val products: List<Product> = emptyList()
)