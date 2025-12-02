package com.example.level_up_appmovil.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable //
import androidx.compose.animation.core.FastOutLinearInEasing //
import androidx.compose.animation.core.LinearOutSlowInEasing //
import androidx.compose.animation.core.tween //
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect //
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
    // Colores definidos en el documento original
    val backgroundColor = Color(0xFF000000)
    val primaryTextColor = Color(0xFF1AAF25)
    val accentColor = Color(0xFF00488D)

    val context = LocalContext.current
    val scope = rememberCoroutineScope() // Scope para lanzar la animación sin interrupciones

    // --- CONFIGURACIÓN DE ANIMACIÓN ---
    // 1. Estado animable para el color de superposición (empieza transparente)
    val screenOverlayColor = remember { Animatable(Color.Transparent) }
    // 2. Color del flash (Rojo semitransparente)
    val errorFlashColor = Color.Red.copy(alpha = 0.5f)

    // 1. Manejo de Errores y Animación
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            // A) Disparar animación de error (Flash Rojo)
            // Usamos 'scope.launch' para que la animación termine aunque 'errorMessage' se limpie rápido
            scope.launch {
                screenOverlayColor.animateTo(
                    targetValue = errorFlashColor,
                    animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
                )
                screenOverlayColor.animateTo(
                    targetValue = Color.Transparent,
                    animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
                )
            }

            // B) Mostrar mensaje al usuario
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()

            // C) Limpiar el error del estado
            viewModel.dismissError()
        }
    }

    // 2. Manejo de Éxito (Navegación)
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess() // Navega a la pantalla Home
            viewModel.consumeLoginSuccess() // Resetea el flag para evitar bucles
        }
    }

    // 3. Interfaz de Usuario
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor), // Fondo base negro
        contentAlignment = Alignment.Center
    ) {
        // --- CAPA DE ANIMACIÓN (NUEVA) ---
        // Se superpone al fondo pero está detrás del contenido.
        // Su color cambia dinámicamente según 'screenOverlayColor'.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(screenOverlayColor.value)
        )

        // --- CONTENIDO DEL FORMULARIO ---
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

            OutlinedTextField(
                value = uiState.loginEmail,
                onValueChange = { viewModel.onLoginEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                // Ajuste visual para que se vea bien sobre fondo negro/rojo
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

            if (uiState.isLoading) {
                CircularProgressIndicator(color = accentColor)
            } else {
                Button(
                    onClick = {
                        // Solo llamamos a la acción, la navegación la maneja el LaunchedEffect
                        viewModel.onLoginClick()
                    },
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