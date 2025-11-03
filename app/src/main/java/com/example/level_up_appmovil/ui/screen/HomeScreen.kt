package com.example.level_up_appmovil.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Definimos las rutas de la navegación interna
object HomeRoutes {
    const val CATALOGO = "catalogo"
    const val PERFIL = "perfil"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    // Variable para saber qué pantalla está seleccionada
    val (selectedScreen, setSelectedScreen) = rememberSaveable {
        mutableStateOf(HomeRoutes.CATALOGO)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                // Botón 1: Catálogo
                NavigationBarItem(
                    selected = selectedScreen == HomeRoutes.CATALOGO,
                    onClick = { setSelectedScreen(HomeRoutes.CATALOGO) },
                    icon = { Icon(Icons.Default.List, "Catálogo") },
                    label = { Text("Catálogo") }
                )
                // Botón 2: Perfil
                NavigationBarItem(
                    selected = selectedScreen == HomeRoutes.PERFIL,
                    onClick = { setSelectedScreen(HomeRoutes.PERFIL) },
                    icon = { Icon(Icons.Default.Person, "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingValues ->
        // Contenido de la pantalla
        Box(modifier = Modifier.padding(paddingValues)) {
            // Mostramos la pantalla correcta según la selección
            when (selectedScreen) {
                HomeRoutes.CATALOGO -> CatalogoScreen()
                HomeRoutes.PERFIL -> PerfilScreen(onLogout = onLogout)
            }
        }
    }
}