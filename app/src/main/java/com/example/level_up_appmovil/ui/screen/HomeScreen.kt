package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star // Icono para "Local"
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.viewmodel.CartViewModel

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    // 0: Global (API), 1: Local (Tus Productos), 2: Carrito, etc.
    var selectedTab by remember { mutableIntStateOf(0) }

    val cartViewModel: CartViewModel = viewModel()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                // 1. API (FakeStore)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, "Global") },
                    label = { Text("Global") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )

                // 2. LOCAL (Tus productos del Drawable) - NUEVO
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, "Local") },
                    label = { Text("Local") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )

                // 3. Carrito
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, "Carrito") },
                    label = { Text("Carrito") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )

                // 4. QR
                NavigationBarItem(
                    icon = { Icon(Icons.Default.QrCodeScanner, "Scan") },
                    label = { Text("Scan") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )

                // 5. Perfil
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, "Perfil") },
                    label = { Text("Perfil") },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 }
                )

                // 6. Admin
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, "Admin") },
                    label = { Text("Admin") },
                    selected = selectedTab == 5,
                    onClick = { selectedTab = 5 }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> CatalogoScreen( // Productos de Internet
                    onAddToCart = { product -> cartViewModel.addToCart(product) }
                )
                1 -> NuestrosProductosScreen( // <--- TUS PRODUCTOS LOCALES
                    onAddToCart = { product -> cartViewModel.addToCart(product) }
                )
                2 -> CartScreen(viewModel = cartViewModel)
                3 -> QrScannerScreen()
                4 -> PerfilScreen(onLogout = onLogout)
                5 -> AdminScreen()
            }
        }
    }
}