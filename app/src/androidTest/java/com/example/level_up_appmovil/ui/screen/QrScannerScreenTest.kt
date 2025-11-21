package com.example.level_up_appmovil.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.level_up_appmovil.viewmodel.QrViewModel
import org.junit.Rule
import org.junit.Test

class QrScannerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun QrScannerScreen_manejoDePermisos() {
        // Este test verifica que si NO hay permisos (default en tests),
        // se muestre la UI de solicitud de permisos en lugar de crashear intentando abrir la cámara.

        composeTestRule.setContent {
            QrScannerScreen(viewModel = QrViewModel())
        }

        // En entorno de test, el permiso suele estar denegado.
        // Verificamos que aparezca el botón o texto de solicitud.
        // Esto confirma que la pantalla cargó y entró en el flujo correcto (if !hasPermission).
        composeTestRule.onNodeWithText("Se requiere acceso a la cámara").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dar Permiso").assertIsDisplayed()
    }
}