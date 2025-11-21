package com.example.level_up_appmovil.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.level_up_appmovil.viewmodel.CartViewModel
import com.example.level_up_appmovil.viewmodel.CatalogoViewModel
import org.junit.Rule
import org.junit.Test

class CartScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun CartScreen_muestraEstadoVacio() {
        composeTestRule.setContent {
            // ViewModel nuevo = carrito vacío
            CartScreen(viewModel = CartViewModel())
        }

        composeTestRule.onNodeWithText("Carrito de Compras").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
    }
}