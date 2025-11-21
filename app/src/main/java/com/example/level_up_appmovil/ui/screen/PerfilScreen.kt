package com.example.level_up_appmovil.ui.screen

import android.app.Application // <-- 1. IMPORTAR APPLICATION
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // <-- 2. IMPORTAR LOCALCONTEXT
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider // <-- 3. IMPORTAR VIEWMODELPROVIDER
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    onLogout: () -> Unit // Lambda para notificar a la navegación que se cerró sesión
) {
    // --- ¡ESTE ES EL CAMBIO! ---
    // Obtenemos el contexto y la factory para el AndroidViewModel
    val context = LocalContext.current
    val factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)

    // Instanciamos el PerfilViewModel pasándole la factory
    val viewModel: PerfilViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()

    // Colores
    val primaryColor = Color(0xFF1E90FF)
    val accentColor = Color(0xFF39FF14)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ... (El resto del archivo es idéntico)
        Text(
            text = "Mi Perfil",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bienvenido,",
            fontSize = 20.sp,
            color = Color.Green
        )
        Text(
            text = uiState.email,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isDuocMember) {
            Text(
                text = "¡Eres miembro Duoc! Tienes 20% DCTO.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al fondo

        Button(
            onClick = {
                viewModel.onLogoutClick()
                onLogout() // Ejecuta la navegación de salida
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Cerrar Sesión", color = Color.White)
        }
    }
}