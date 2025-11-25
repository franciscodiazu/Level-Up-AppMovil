package com.example.level_up_appmovil.data.api.repository

import com.example.level_up_appmovil.data.api.RetrofitClient
import com.example.level_up_appmovil.data.api.model.Product

class ProductRepository {
    private val api = RetrofitClient.instance

    // Ahora es una función suspendida porque internet demora
    suspend fun getProducts(): List<Product> {
        return try {
            api.getProducts()
        } catch (e: Exception) {
            // Si falla (sin internet), retornamos lista vacía o podrías manejar error
            emptyList()
        }
    }
}