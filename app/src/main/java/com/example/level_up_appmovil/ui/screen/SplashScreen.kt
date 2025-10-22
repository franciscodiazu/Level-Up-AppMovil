package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up_appmovil.R // Asegúrate de que esta sea la ruta correcta a tu R
import kotlinx.coroutines.delay

// --- Pantalla de Inicio (Splash Screen) ---
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // LaunchedEffect se usa para ejecutar una acción una sola vez cuando el Composable aparece.
    // Aquí, esperaremos 3 segundos antes de navegar a la siguiente pantalla.
    LaunchedEffect(Unit) {
        delay(3000) // Espera 3 segundos
        onTimeout() // Llama a la función para navegar
    }

    // Usamos un Box para centrar el contenido fácilmente.
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Se establece el color de fondo a negro según el documento.
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Columna para organizar el logo y el texto verticalmente.
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Carga y muestra la imagen del logo desde los recursos 'drawable'.
            Image(
                painter = painterResource(id = R.drawable.logo_levelup),
                contentDescription = "Logo de Level-Up Gamer",
                modifier = Modifier.size(200.dp) // Ajusta el tamaño del logo como prefieras
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el logo y el texto

            // Texto opcional, usando la paleta de colores del documento.
            Text(
                text = "LEVEL-UP GAMER",
                // Se usa el color blanco para el texto principal[cite: 84].
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
                // Para usar la fuente "Orbitron", necesitarías agregarla a tus recursos de fuentes.
            )
        }
    }
}

// --- Previsualización para el diseñador de Android Studio ---
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {}) // La previsualización no necesita navegar
}