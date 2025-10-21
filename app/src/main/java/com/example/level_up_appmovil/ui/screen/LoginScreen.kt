package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onRegisterClick: () -> Unit, onLoginSuccess: () -> Unit) {
    // Colores definidos en el documento
    val backgroundColor = Color(0xFF000000)
    val primaryTextColor = Color(0xFFFFFFFF)
    val accentColor = Color(0xFF1E90FF) // Azul Eléctrico para botones

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Título de la pantalla (usaría la fuente Orbitron)
            Text(
                text = "INICIAR SESIÓN",
                color = primaryTextColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de texto para el Email
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la Contraseña
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para iniciar sesión
            Button(
                onClick = { onLoginSuccess() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text("Entrar", color = primaryTextColor)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto para navegar a la pantalla de registro
            TextButton(onClick = onRegisterClick) {
                Text("¿No tienes cuenta? Regístrate", color = accentColor)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onRegisterClick = {}, onLoginSuccess = {})
}