package com.example.level_up_appmovil.ui.screen // Revisa tu package

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.model.AuthUiState
import com.example.level_up_appmovil.viewmodel.AuthViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Colores del documento
    val backgroundColor = Color(0xFFFCFCFD)
    val primaryTextColor = Color(0xFF1E90FF)
    val accentColor = Color(0xFF39FF14) // Verde Neón para registro

    // Observador de errores o éxito
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.dismissError() // Limpia el error después de mostrarlo
            if (uiState.registrationSuccess) {
                onRegisterSuccess() // Navega de vuelta al Login
            }
        }
    }

    // --- DIALOGO DE CALENDARIO ---
    if (uiState.showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis()
        )
        DatePickerDialog(
            onDismissRequest = { viewModel.showDatePicker(false) },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = LocalDate.ofEpochDay(it / (1000 * 60 * 60 * 24))
                        viewModel.onDateSelected(selectedDate)
                    }
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showDatePicker(false) }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // --- UI DE LA PANTALLA ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "REGISTRO",
                color = primaryTextColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = uiState.regEmail,
                onValueChange = { viewModel.onRegEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Fecha de Nacimiento (Fake TextField)
            OutlinedTextField(
                value = uiState.birthDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "",
                onValueChange = {},
                label = { Text("Fecha de Nacimiento (MM/DD/YYYY)") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, "Select Date")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.showDatePicker(true) },
                readOnly = true,
                enabled = false // Se deshabilita para forzar el clic
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.regPass,
                onValueChange = { viewModel.onRegPassChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.regConfirmPass,
                onValueChange = { viewModel.onRegConfirmPassChange(it) },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = accentColor)
            } else {
                Button(
                    onClick = { viewModel.onRegisterClick() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Registrarse", color = Color.Black)
                }
            }

            TextButton(onClick = onBackToLoginClick) {
                Text("¿Ya tienes cuenta? Inicia Sesión", color = Color.Blue)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        viewModel = viewModel(), // ViewModel de preview
        onRegisterSuccess = {},
        onBackToLoginClick = {}
    )
}