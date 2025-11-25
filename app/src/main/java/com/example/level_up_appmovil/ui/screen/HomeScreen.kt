package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
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
    // Estado para saber qué pestaña está seleccionada (0=Inicio, 1=Perfil, 2=Carrito, 3=QR, 4=Admin)
    var selectedTab by remember { mutableIntStateOf(0) }

    // ViewModel del carrito compartido
    val cartViewModel: CartViewModel = viewModel()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                // 1. Catálogo (Inicio) -> Tab 0
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, "Catálogo") },
                    label = { Text("Inicio") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )

                // 2. Carrito -> Tab 2 (Mantengo tu orden visual original)
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, "Carrito") },
                    label = { Text("Carrito") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )

                // 3. Scanner QR -> Tab 3
                NavigationBarItem(
                    icon = { Icon(Icons.Default.QrCodeScanner, "Scan") },
                    label = { Text("QR") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )

                // 4. Perfil -> Tab 1
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, "Perfil") },
                    label = { Text("Perfil") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )

                // 5. Admin (NUEVO) -> Tab 4
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, "Admin") },
                    label = { Text("Admin") },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 }
                )
            }
        }
    ) { paddingValues ->
        // Contenido principal que cambia según la pestaña
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> CatalogoScreen(
                    onAddToCart = { product -> cartViewModel.addToCart(product) }
                )
                1 -> PerfilScreen(onLogout = onLogout)
                2 -> CartScreen(viewModel = cartViewModel)
                3 -> QrScannerScreen()
                4 -> AdminScreen() // <--- Aquí cargamos la pantalla de gestión de productos
            }
        }
    }
}