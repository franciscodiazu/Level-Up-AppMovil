package com.example.level_up_appmovil.navigation // Asegúrate que tu package sea el correcto

// --- IMPORTACIONES ---
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel // <-- Importante para el ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions // <-- ¡IMPORTANTE PARA EL ERROR EN ROJO!
import com.example.level_up_appmovil.ui.screen.LoginScreen
import com.example.level_up_appmovil.ui.screen.RegisterScreen
import com.example.level_up_appmovil.ui.screen.SplashScreen
import com.example.level_up_appmovil.viewmodel.AuthViewModel

// Objeto para mantener las rutas organizadas
object AppRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
}

// --- NAVEGACIÓN PRINCIPAL ---
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // --- CORRECCIÓN 1 ---
    // Instanciamos el ViewModel aquí, una sola vez.
    // Así, LoginScreen y RegisterScreen compartirán el mismo ViewModel y estado.
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        // --- Pantalla SPLASH ---
        composable(AppRoutes.SPLASH) {
            SplashScreen(onTimeout = {
                val navOptions = navOptions {
                    popUpTo(AppRoutes.SPLASH) { inclusive = true }
                }
                navController.navigate(AppRoutes.LOGIN, navOptions)
            })
        }

        // --- Pantalla LOGIN ---
        composable(AppRoutes.LOGIN) {

            // --- CORRECCIÓN 2 ---
            // Obtenemos el 'uiState' actual desde el ViewModel.
            val uiState by authViewModel.uiState.collectAsState()

            // Ahora 'uiState' y 'authViewModel' SÍ existen y se pueden pasar.
            LoginScreen(
                uiState = uiState,
                viewModel = authViewModel,
                onRegisterClick = {
                    navController.navigate(AppRoutes.REGISTER)
                },
                onLoginSuccess = {
                    val navOptions = navOptions {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                    navController.navigate(AppRoutes.HOME, navOptions)
                }
            )
        }

        // --- Pantalla REGISTER ---
        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                viewModel = authViewModel, // Le pasamos el mismo ViewModel
                onRegisterSuccess = {
                    navController.popBackStack() // Regresa a Login
                },
                onBackToLoginClick = {
                    navController.popBackStack() // Regresa a Login
                }
            )
        }

        // --- Pantalla HOME (Catálogo, etc.) ---
        composable(AppRoutes.HOME) {
            // Aquí irá tu pantalla de Catálogo de productos
            // Por ejemplo: CatalogScreen()
        }
    }
}