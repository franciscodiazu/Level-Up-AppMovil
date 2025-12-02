package com.example.level_up_appmovil.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable // Necesario para la animación de color
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up_appmovil.model.AuthUiState
import com.example.level_up_appmovil.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    viewModel: AuthViewModel,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Definición de colores del tema
    val backgroundColor = Color(0xFF000000)
    val primaryTextColor = Color(0xFF1AAF25)
    val accentColor = Color(0xFF00488D)

    val context = LocalContext.current

    // Scope para ejecutar la animación independientemente de la recomposición
    val scope = rememberCoroutineScope()

    // --- CONFIGURACIÓN DE LA ANIMACIÓN ---
    // 1. Variable que controla el color de la capa superior (empieza transparente)
    val screenOverlayColor = remember { Animatable(Color.Transparent) }

    // 2. Definimos el color del "Flash" (Rojo semitransparente)
    val errorFlashColor = Color.Red.copy(alpha = 0.5f)

    // 1. Manejo de Errores con Animación
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            // A) Disparamos la animación visual
            scope.launch {
                // Fase 1: Cambiar a rojo rápido (golpe visual)
                screenOverlayColor.animateTo(
                    targetValue = errorFlashColor,
                    animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
                )
                // Fase 2: Desvanecer a transparente suavemente
                screenOverlayColor.animateTo(
                    targetValue = Color.Transparent,
                    animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
                )
            }

            // B) Mostramos el mensaje de texto (Toast)
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()

            // C) Limpiamos el estado del error en el ViewModel
            viewModel.dismissError()
        }
    }

    // 2. Manejo de Éxito (Navegación)
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess() // Navegar al Home
            viewModel.consumeLoginSuccess()
        }
    }

    // 3. Estructura Visual
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor), // Fondo negro base
        contentAlignment = Alignment.Center
    ) {
        // --- CAPA DE ANIMACIÓN (NUEVA) ---
        // Esta caja cubre toda la pantalla. Su color de fondo es dinámico.
        // Normalmente es transparente, pero se vuelve roja cuando hay error.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(screenOverlayColor.value)
        )

        // --- CONTENIDO DEL LOGIN ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "INICIAR SESIÓN",
                color = primaryTextColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Campo Email
            OutlinedTextField(
                value = uiState.loginEmail,
                onValueChange = { viewModel.onLoginEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = primaryTextColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = primaryTextColor,
                    unfocusedLabelColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = uiState.loginPass,
                onValueChange = { viewModel.onLoginPassChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = primaryTextColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = primaryTextColor,
                    unfocusedLabelColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Botón o Carga
            if (uiState.isLoading) {
                CircularProgressIndicator(color = accentColor)
            } else {
                Button(
                    onClick = { viewModel.onLoginClick() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Entrar", color = primaryTextColor)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onRegisterClick) {
                Text("¿No tienes cuenta? Regístrate", color = accentColor)
            }
        }
    }
}