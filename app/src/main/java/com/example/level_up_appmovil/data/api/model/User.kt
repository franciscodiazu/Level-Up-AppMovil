package com.example.level_up_appmovil.data.api.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val pass: String,
    val birthDate: LocalDate,
    val isDuocMember: Boolean,
    val photoUri: String? = null
)