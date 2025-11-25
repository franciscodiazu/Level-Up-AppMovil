package com.example.level_up_appmovil.data.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_products")
data class LocalProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val price: Int,
    val imageUri: String? = null 
)
