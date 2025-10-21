package com.example.level_up_appmovil.navigation

sealed class AppRoutes(val route: String) {
    object SPLASH : AppRoutes("splash")
    object LOGIN : AppRoutes("login")
    object HOME : AppRoutes("home")
}