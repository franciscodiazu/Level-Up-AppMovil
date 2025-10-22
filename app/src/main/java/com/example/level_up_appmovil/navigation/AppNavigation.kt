package com.example.level_up_appmovil.navigation // Revisa tu package

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.level_up_appmovil.ui.screen.LoginScreen
import com.example.level_up_appmovil.ui.screen.RegisterScreen
import com.example.level_up_appmovil.ui.screen.SplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.viewmodel.AuthViewModel



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Instanciamos el ViewModel aquí.
    // NOTA: Esto hará que Login y Register usen la MISMA instancia.
    // Esto no es lo ideal, pero es lo más simple de implementar.
    // Una mejor forma sería usar un grafo de navegación anidado.
    // Por ahora, ¡funciona!
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(AppRoutes.SPLASH) {
            SplashScreen(onTimeout = {
                val navOptions = navOptions {
                    popUpTo(AppRoutes.SPLASH) { inclusive = true }
                }
                navController.navigate(AppRoutes.LOGIN, navOptions)
            })
        }

        composable(AppRoutes.LOGIN) {
            val uiState by authViewModel.uiState.collectAsState()

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

        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.popBackStack() // Regresa a Login
                },
                onBackToLoginClick = {
                    navController.popBackStack() // Regresa a Login
                }
            )
        }

        // Aquí irán las pantallas de Catálogo y Perfil
        composable(AppRoutes.HOME) {
            // Ejemplo: CatalogScreen()
        }
    }
}