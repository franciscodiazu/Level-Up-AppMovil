package com.example.level_up_appmovil.ui.screen

import androidx.test.core.app.ApplicationProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.level_up_appmovil.model.AuthUiState
import com.example.level_up_appmovil.viewmodel.AuthViewModel
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun LoginScreen_muestraElementosBasicos() {
        composeTestRule.setContent {
            // Instanciamos el ViewModel con el contexto de prueba
            val viewModel = AuthViewModel(ApplicationProvider.getApplicationContext())

            LoginScreen(
                uiState = AuthUiState(), // Estado inicial limpio
                viewModel = viewModel,
                onRegisterClick = {},
                onLoginSuccess = {}
            )
        }

        // Verificamos Título, Botón y Labels
        composeTestRule.onNodeWithText("INICIAR SESIÓN").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entrar").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").assertIsDisplayed()
    }
}
