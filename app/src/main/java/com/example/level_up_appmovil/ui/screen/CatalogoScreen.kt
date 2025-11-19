package com.example.level_up_appmovil.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.data.api.model.Product // Asegúrate de importar esto
import com.example.level_up_appmovil.ui.components.ProductCard
import com.example.level_up_appmovil.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(
    viewModel: CatalogoViewModel = viewModel(),
    onAddToCart: (Product) -> Unit // <--- Recibimos la acción de agregar
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFD))
            .padding(16.dp)
    ) {
        Text(
            text = "Catálogo de Productos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E90FF)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.products) { product ->
                ProductCard(
                    product = product,
                    onAddClick = {
                        onAddToCart(product)
                        Toast.makeText(context, "Agregado: ${product.name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}