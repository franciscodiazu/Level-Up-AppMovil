package com.example.level_up_appmovil.viewmodel

import com.example.level_up_appmovil.data.api.model.Product
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CartViewModelTest {

    @Test
    fun `Agregar productos actualiza el precio total correctamente`() = runTest {
        // 1. GIVEN (DADO): Un carrito vacío y dos productos simples
        // Como 'Product' es una data class simple, la creamos directamente sin complicarnos con Mocks
        val viewModel = CartViewModel()
        val juego = Product(
            id = 1,
            name = "Elden Ring",
            category = "Juegos",
            description = "GOTY",
            price = 50000.0, // Precio Double
            image = ""
        )
        val control = Product(
            id = 2,
            name = "Mando PS5",
            category = "Accesorios",
            description = "DualSense",
            price = 45000.0, // Precio Double
            image = ""
        )

        // 2. WHEN (CUANDO): Agregamos los productos al carrito
        viewModel.addToCart(juego)
        viewModel.addToCart(control)

        // 3. THEN (ENTONCES): El total debe ser la suma exacta (95.000)
        val totalEsperado = 95000.0
        val totalReal = viewModel.getTotal()

        // Verificamos que sean iguales (el 0.0 es el margen de error para decimales)
        assertEquals(totalEsperado, totalReal, 0.0)
    }
}