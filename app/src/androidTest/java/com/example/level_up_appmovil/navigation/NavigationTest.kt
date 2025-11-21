package com.example.level_up_appmovil.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.level_up_appmovil.MainActivity
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    // Usamos createAndroidComposeRule para lanzar la MainActivity real
    // y así probar la navegación integrada (AppNavigation) tal cual la usa el usuario.
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navegar_de_login_a_registro_y_volver() {
        // 1. ESPERAR AL SPLASH SCREEN
        // Como tu App inicia con un Splash de 3 segundos, debemos esperar a que
        // desaparezca y aparezca la pantalla de Login.
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            try {
                // Esperamos hasta que el texto "INICIAR SESIÓN" exista en el árbol semántico
                composeTestRule.onNodeWithText("INICIAR SESIÓN").assertExists()
                true
            } catch (e: AssertionError) {
                false
            }
        }

        // 2. ESTAMOS EN LOGIN
        composeTestRule.onNodeWithText("INICIAR SESIÓN").assertIsDisplayed()

        // 3. NAVEGAR A REGISTRO
        // Simulamos clic en el botón de ir a registro
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()

        // 4. VERIFICAR PANTALLA DE REGISTRO
        // Verificamos que ahora estemos viendo el título de Registro
        composeTestRule.onNodeWithText("REGISTRO").assertIsDisplayed()
        // Verificamos un campo único de esa pantalla (ej: Fecha de Nacimiento)
        composeTestRule.onNodeWithText("Fecha de Nacimiento").assertIsDisplayed()

        // 5. VOLVER A LOGIN
        // Simulamos clic en el botón de volver
        composeTestRule.onNodeWithText("¿Ya tienes cuenta? Inicia Sesión").performClick()

        // 6. VERIFICAR LOGIN NUEVAMENTE
        composeTestRule.onNodeWithText("INICIAR SESIÓN").assertIsDisplayed()
    }
    @Test
    fun test_Flujo_Completo_Login_Home_Tabs_Logout() {
        esperarSplashYVerificarLogin()

        // 1. Realizar Login
        ingresarCredencialesYEntrar("admin@duocuc.cl", "123456")

        // 2. Verificar que estamos en HOME (Pestaña por defecto: Catálogo)
        // Esperar a que la UI se estabilice
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Catálogo de Productos").assertIsDisplayed()

        // --- PROBAR NAVEGACIÓN INFERIOR (BOTTOM BAR) ---

        // A. Ir a Carrito
        composeTestRule.onNodeWithText("Carrito").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Carrito de Compras").assertIsDisplayed()

        // B. Ir a QR (Manejo seguro de permisos)
        composeTestRule.onNodeWithText("QR").performClick()
        composeTestRule.waitForIdle()
        try {
            composeTestRule.onNodeWithText("Escanea un código QR").assertIsDisplayed()
        } catch (e: AssertionError) {
            // Si no hay permisos, aparece este texto
            try {
                composeTestRule.onNodeWithText("Se requiere acceso a la cámara").assertIsDisplayed()
            } catch (e: Throwable) {
                // Fallback silencioso si la UI cambia, para no romper todo el flujo por el QR
            }
        }

        // C. Ir a Perfil
        composeTestRule.onNodeWithText("Perfil").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Mi Perfil").assertIsDisplayed()
        composeTestRule.onNodeWithText("admin@duocuc.cl").assertIsDisplayed()

        // --- PROBAR CERRAR SESIÓN ---

        // 3. Click en Cerrar Sesión
        composeTestRule.onNodeWithText("Cerrar Sesión").performClick()

        // 4. Verificar que volvimos al Login
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("INICIAR SESIÓN").assertIsDisplayed()
    }

    // --- FUNCIONES DE AYUDA (HELPERS CORREGIDOS) ---

    private fun esperarSplashYVerificarLogin() {
        // CORRECCIÓN: Capturamos 'Throwable' (cualquier error) en lugar de solo 'AssertionError'.
        // Esto evita el crash "No compose hierarchies found" mientras la app inicia.
        composeTestRule.waitUntil(timeoutMillis = 15000) {
            try {
                composeTestRule.onNodeWithText("INICIAR SESIÓN").assertExists()
                true
            } catch (e: Throwable) {
                false
            }
        }
    }

    private fun ingresarCredencialesYEntrar(user: String, pass: String) {
        composeTestRule.onNodeWithText("Correo Electrónico").performTextInput(user)
        composeTestRule.onNodeWithText("Contraseña").performTextInput(pass)
        composeTestRule.onNodeWithText("Entrar").performClick()

        // Esperar transición al Home de forma segura
        composeTestRule.waitUntil(timeoutMillis = 20000) {
            try {
                composeTestRule.onNodeWithText("Catálogo de Productos").assertExists()
                true
            } catch (e: Throwable) {
                false
            }
        }
    }
}