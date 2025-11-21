package com.example.level_up_appmovil.ui.screen

import androidx.test.core.app.ApplicationProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.level_up_appmovil.viewmodel.PerfilViewModel
import com.example.level_up_appmovil.viewmodel.QrViewModel
import org.junit.Rule
import org.junit.Test

class PerfilScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun PerfilScreen_muestraInformacionBasica() {
        // Debemos usar runOnIdle o similar si queremos esperar datos,
        // pero para UI básica basta con setContent
        composeTestRule.setContent {
            // IMPORTANTE: PerfilScreen intenta obtener ViewModel internamente con viewModel(factory).
            // Para testear, necesitamos que PerfilScreen acepte el ViewModel como parámetro o
            // Mockear el ViewModelStore.
            // Como tu código actual de PerfilScreen crea el VM internamente:
            // val viewModel: PerfilViewModel = viewModel(factory = factory)
            // El test de integración real lanzará la pantalla y creará el VM real usando el contexto del test.

            PerfilScreen(onLogout = {})
        }

        composeTestRule.onNodeWithText("Mi Perfil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cerrar Sesión").assertIsDisplayed()
    }
}