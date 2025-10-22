package com.example.level_up_appmovil.ui.screen // Revisa tu package

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.model.AuthUiState
import com.example.level_up_appmovil.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    viewModel: AuthViewModel,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Colores definidos en el documento
    val backgroundColor = Color(0xFFFCFCFD)
    val primaryTextColor = Color(0xFF1E90FF)
    val accentColor = Color(0xFF39FF14)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.loginPass,
                onValueChange = { viewModel.onLoginPassChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = accentColor)
            } else {
                Button(
                    onClick = {
                        viewModel.onLoginClick()
                        // Simulación de login exitoso (borrar esto cuando conectes API)
                        onLoginSuccess()
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

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = AuthUiState(),
        viewModel = viewModel(), // VM de preview
        onRegisterClick = {},
        onLoginSuccess = {}
    )
}