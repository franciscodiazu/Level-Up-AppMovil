package com.example.level_up_appmovil.ui.screen

import android.app.Application
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.level_up_appmovil.data.api.model.LocalProduct
import com.example.level_up_appmovil.viewmodel.AdminProductViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AdminScreen() {
    val context = LocalContext.current
    val factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
    val viewModel: AdminProductViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> if (uri != null) viewModel.onImageSelected(uri) }
    )

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.messageShown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(16.dp)
    ) {
        Text(
            text = "Gestión de Productos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E90FF)
        )

        // --- FORMULARIO ---
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Botón Foto
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.Gray, RoundedCornerShape(8.dp))
                            .clickable {
                                photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.imageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(uiState.imageUri),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Foto", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        OutlinedTextField(
                            value = uiState.name,
                            onValueChange = { viewModel.onNameChange(it) },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    OutlinedTextField(
                        value = uiState.category,
                        onValueChange = { viewModel.onCategoryChange(it) },
                        label = { Text("Categoría") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = uiState.price,
                        onValueChange = { viewModel.onPriceChange(it) },
                        label = { Text("Precio") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    if (uiState.isEditing) {
                        Button(
                            onClick = { viewModel.clearForm() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            modifier = Modifier.weight(1f)
                        ) { Text("Cancelar") }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Button(
                        onClick = { viewModel.saveProduct() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF39FF14)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (uiState.isEditing) "Actualizar" else "Guardar", color = Color.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        // --- LISTA DE PRODUCTOS LOCALES ---
        LazyColumn {
            items(uiState.products) { product ->
                AdminProductItem(
                    product = product,
                    onEdit = { viewModel.onProductSelected(product) },
                    onDelete = { viewModel.deleteProduct(product) }
                )
            }
        }
    }
}

@Composable
fun AdminProductItem(product: LocalProduct, onEdit: () -> Unit, onDelete: () -> Unit) {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (product.imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(product.imageUri),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp).clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(Modifier.size(50.dp).background(Color.LightGray))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(format.format(product.price), color = Color.Gray, fontSize = 12.sp)
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Blue)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
            }
        }
    }
}