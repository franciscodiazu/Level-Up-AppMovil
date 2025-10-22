package com.example.level_up_appmovil // Revisa que tu package sea el correcto

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.level_up_appmovil.navigation.AppNavigation // <-- Importa tu Navegación
import com.example.level_up_appmovil.ui.theme.LevelUpAppMovilTheme
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí se carga el tema de tu app (definido en ui/theme/Theme.kt)
            LevelUpAppMovilTheme{

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // El color de fondo se toma del tema
                    color = MaterialTheme.colorScheme.background
                ) {
                    // --- ¡ESTA ES LA LÍNEA CLAVE! ---
                    // Aquí le dices a la app que inicie tu
                    // sistema de navegación.
                    AppNavigation()
                }
            }
        }
    }
}
