package com.example.level_up_appmovil.navigation // Revisa tu package

import android.app.Application // <-- 1. IMPORTAR APPLICATION
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext // <-- 2. IMPORTAR LOCALCONTEXT
import androidx.lifecycle.ViewModelProvider // <-- 3. IMPORTAR VIEWMODELPROVIDER
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.level_up_appmovil.ui.screen.HomeScreen
import com.example.level_up_appmovil.ui.screen.LoginScreen
import com.example.level_up_appmovil.ui.screen.RegisterScreen
import com.example.level_up_appmovil.ui.screen.SplashScreen
import com.example.level_up_appmovil.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // --- ¡ESTE ES EL CAMBIO! ---
    // Obtenemos el contexto y la factory para el AndroidViewModel
    val context = LocalContext.current
    val factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)

    // Instanciamos el AuthViewModel pasándole la factory
    val authViewModel: AuthViewModel = viewModel(factory = factory)

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

        composable(AppRoutes.HOME) {
            HomeScreen(
                onLogout = {
                    // Si el usuario cierra sesión, lo mandamos al Login
                    val navOptions = navOptions {
                        popUpTo(AppRoutes.HOME) { inclusive = true }
                    }
                    navController.navigate(AppRoutes.LOGIN, navOptions)
                }
            )
        }
    }
}