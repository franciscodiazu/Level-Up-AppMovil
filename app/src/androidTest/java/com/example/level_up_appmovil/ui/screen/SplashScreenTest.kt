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

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun SplashScreen_muestraLogoYTitulo() {
        composeTestRule.setContent {
            SplashScreen(onTimeout = {})
        }

        composeTestRule.onNodeWithText("LEVEL-UP GAMER").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Logo de Level-Up Gamer").assertIsDisplayed()
    }
}
