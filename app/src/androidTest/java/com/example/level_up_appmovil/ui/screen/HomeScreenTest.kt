package com.example.level_up_appmovil.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun HomeScreen_muestraTitulo() {
        composeTestRule.setContent {
            // Tu HomeScreen requiere un lambda onLogout, no un navController
            HomeScreen(onLogout = {})
        }
        // Verificamos que el título del catálogo se muestre (equivalente a "PokéStore")
        composeTestRule.onNodeWithText("Catálogo de Productos").assertIsDisplayed()
    }

    @Test
    fun HomeScreen_muestraDisponible() {
        composeTestRule.setContent {
            HomeScreen(onLogout = {})
        }
        // Verificamos que la pestaña "Inicio" esté visible (equivalente a "Pokémon disponible")
        composeTestRule.onNodeWithText("Inicio").assertIsDisplayed()
    }
}