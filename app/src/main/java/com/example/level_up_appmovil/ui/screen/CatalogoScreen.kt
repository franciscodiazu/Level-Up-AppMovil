package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.ui.components.ProductCard
import com.example.level_up_appmovil.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(
    viewModel: CatalogoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFD)) // Fondo de la app
            .padding(16.dp)
    ) {
        Text(
            text = "Catálogo de Productos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E90FF) // Azul primario
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de productos
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.products) { product ->
                ProductCard(product = product)
            }
        }
    }
}