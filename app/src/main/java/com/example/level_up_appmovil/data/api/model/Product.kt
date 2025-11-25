package com.example.level_up_appmovil.data.api.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int, // La API usa Int para ID
    @SerializedName("title") val name: String, // Mapeamos "title" de la API a "name"
    val category: String,
    val description: String,
    val price: Double,
    @SerializedName("image") val image: String // Ahora es String (URL)
)