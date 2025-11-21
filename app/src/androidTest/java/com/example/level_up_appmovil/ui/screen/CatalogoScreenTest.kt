package com.example.level_up_appmovil.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.level_up_appmovil.viewmodel.CartViewModel
import com.example.level_up_appmovil.viewmodel.CatalogoViewModel
import org.junit.Rule
import org.junit.Test

class CatalogoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun CatalogoScreen_cargaProductos() {
        composeTestRule.setContent {
            // Pasamos un ViewModel nuevo
            CatalogoScreen(
                viewModel = CatalogoViewModel(),
                onAddToCart = {}
            )
        }

        // Esperamos a que la UI se asiente (importante para listas)
        composeTestRule.waitForIdle()

        // 1. Verificamos el Título Principal
        composeTestRule.onNodeWithText("Catálogo de Productos").assertIsDisplayed()

        // 2. Verificamos un producto específico (Catan es el primero en la lista hardcodeada)
        // Usamos useUnmergedTree = true por si el texto es parte de un componente complejo
        composeTestRule.onNodeWithText("Catan").assertIsDisplayed()
    }
}
