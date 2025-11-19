package com.example.level_up_appmovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.level_up_appmovil.data.api.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    // Lista de productos en el carrito (empieza vacía)
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    // Función para agregar un producto
    fun addToCart(product: Product) {
        _cartItems.update { currentList ->
            currentList + product
        }
    }

    // Función para quitar un producto
    fun removeFromCart(product: Product) {
        _cartItems.update { currentList ->
            currentList - product
        }
    }

    // Función para limpiar todo
    fun clearCart() {
        _cartItems.value = emptyList()
    }

    // Calcular precio total
    fun getTotal(): Double {
        return _cartItems.value.sumOf { it.price }
    }
}