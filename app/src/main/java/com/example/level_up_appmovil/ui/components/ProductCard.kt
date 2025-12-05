package com.example.level_up_appmovil.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.level_up_appmovil.data.api.model.Product
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductCard(
    product: Product,
    onAddClick: () -> Unit
) {
    // CAMBIO: Usamos Locale("es", "CL") para formato chileno (ej: $10.990)
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    // Quitamos decimales para que se vea más limpio (en pesos no se usan centavos)
    format.maximumFractionDigits = 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E90FF),
                    maxLines = 2
                )
                Text(
                    text = product.category,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                // Aquí se mostrará el precio con formato chileno
                Text(
                    text = format.format(product.price),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF39FF14)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAddClick,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E90FF))
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Agregar", color = Color.White)
                }
            }
        }
    }
}