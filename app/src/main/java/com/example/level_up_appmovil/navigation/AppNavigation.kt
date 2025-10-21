package com.example.level_up_appmovil.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up_appmovil.ui.screen.SplashScreen
import androidx.navigation.navOptions
import com.example.level_up_appmovil.ui.screen.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH.route
    ) {
        composable(AppRoutes.SPLASH.route) {
            SplashScreen(onTimeout = {
                val navOptions = navOptions {
                    popUpTo(AppRoutes.SPLASH.route) {
                        inclusive = true
                    }
                }
                navController.navigate(AppRoutes.LOGIN.route, navOptions)
            })
        }
        composable(AppRoutes.LOGIN.route) {
            LoginScreen(
                onRegisterClick = TODO(),
                onLoginSuccess = TODO()
            )
        }
    }
}