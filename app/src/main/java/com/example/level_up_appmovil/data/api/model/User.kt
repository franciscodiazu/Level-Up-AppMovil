package com.example.level_up_appmovil.data.api.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)] // Email único
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val pass: String, // En una app real, esto debe ser un hash
    val birthDate: LocalDate,
    val isDuocMember: Boolean,
    val photoUri: String? = null // Para la foto de perfil
)