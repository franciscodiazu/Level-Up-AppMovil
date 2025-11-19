package com.example.level_up_appmovil.ui.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.utils.CameraPermissionHelper
import com.example.level_up_appmovil.utils.QrScanner
import com.example.level_up_appmovil.viewmodel.QrViewModel

@Composable
fun QrScannerScreen(
    viewModel: QrViewModel = viewModel()
) {
    val qrResult by viewModel.qrResult.collectAsState()
    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(CameraPermissionHelper.hasCameraPermission(context))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> hasPermission = isGranted }
    )

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!hasPermission) {
            Text("Se requiere acceso a la cámara", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { permissionLauncher.launch(android.Manifest.permission.CAMERA) }) {
                Text("Dar Permiso")
            }
        } else if (qrResult == null) {
            Text(
                "Escanea un código QR",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E90FF),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(8.dp)
            ) {
                QrScanner(
                    onQrCodeScanned = { result ->
                        viewModel.onQrDetected(result)
                        Toast.makeText(context, "¡Código Detectado!", Toast.LENGTH_SHORT).show()
                    }
                )

                Surface(
                    modifier = Modifier.size(250.dp).align(Alignment.Center),
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(3.dp, Color(0xFF39FF14))
                ) {}
            }
            Text("Apunta la cámara al código", color = Color.Gray)

        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("¡QR Detectado!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E90FF))
                    Spacer(Modifier.height(16.dp))
                    Text(qrResult ?: "", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.clearResult() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E90FF))
                    ) {
                        Text("Escanear otro", color = Color.White)
                    }
                }
            }
        }
    }
}