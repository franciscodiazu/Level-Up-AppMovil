package com.example.level_up_appmovil.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up_appmovil.data.LocalProductsData // Importamos tu lista
import com.example.level_up_appmovil.data.api.model.Product
import com.example.level_up_appmovil.ui.components.ProductCard

@Composable
fun NuestrosProductosScreen(
    onAddToCart: (Product) -> Unit
) {
    val context = LocalContext.current
    // Obtenemos la lista directa del objeto
    val products = LocalProductsData.products

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(16.dp)
    ) {
        Text(
            text = "Productos Level Up",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E90FF)
        )
        Text(
            text = "Exclusivos de la tienda",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
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