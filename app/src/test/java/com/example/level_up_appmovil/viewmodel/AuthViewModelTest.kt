package com.example.level_up_appmovil.viewmodel

import android.app.Application
import com.example.level_up_appmovil.data.api.model.User
import com.example.level_up_appmovil.data.db.AppDatabase
import com.example.level_up_appmovil.data.db.UserDao
import com.example.level_up_appmovil.utils.MainDispatcherRule // Importa tu regla
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks (Simulaciones)
    private lateinit var viewModel: AuthViewModel
    private val context: Application = mockk(relaxed = true)
    private val database: AppDatabase = mockk()
    private val userDao: UserDao = mockk(relaxed = true)

    @Before
    fun setup() {
        // Como tu ViewModel crea la BD internamente con AppDatabase.getDatabase(context),
        // necesitamos mockear ese método estático o ajustar tu arquitectura.
        // TRUCO RÁPIDO: Mockear el Singleton de Room si es posible, o mejor aún,
        // simular el comportamiento interno.

        // *Nota:* Para facilitar el testeo, lo ideal sería pasar el repositorio en el constructor,
        // pero dado tu código actual, usaremos mockkStatic para la BD:
        mockkStatic(AppDatabase::class)
        every { AppDatabase.getDatabase(any()) } returns database
        every { database.userDao() } returns userDao

        viewModel = AuthViewModel(context)
    }

    @Test
    fun `Registro falla si usuario es menor de 18`() = runTest {
        // 1. GIVEN (Dado que ingresamos datos de un menor de edad)
        viewModel.onRegEmailChange("nino@correo.com")
        viewModel.onRegPassChange("Pass123")
        viewModel.onRegConfirmPassChange("Pass123")
        // Fecha actual 2025 -> Nacimiento 2015 = 10 años
        viewModel.onDateSelected(LocalDate.of(2015, 1, 1))

        // 2. WHEN (Cuando intentamos registrar)
        viewModel.onRegisterClick()

        // 3. THEN (Entonces debería haber un error y no éxito)
        val state = viewModel.uiState.value
        assertFalse(state.registrationSuccess)
        assertEquals("Debes ser mayor de 18 años", state.regErrorBirthDate)
    }
}