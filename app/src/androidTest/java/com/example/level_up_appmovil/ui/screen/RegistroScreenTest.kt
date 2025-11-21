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

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun RegisterScreen_muestraFormulario() {
        composeTestRule.setContent {
            val viewModel = AuthViewModel(ApplicationProvider.getApplicationContext())

            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {},
                onBackToLoginClick = {}
            )
        }

        // Verificamos Título y campos específicos de registro
        composeTestRule.onNodeWithText("REGISTRO").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fecha de Nacimiento").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirmar Contraseña").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }
}