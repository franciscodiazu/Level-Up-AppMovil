package com.example.level_up_appmovil.data.api.model

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val price: Double,
    val imageRes: Int
)