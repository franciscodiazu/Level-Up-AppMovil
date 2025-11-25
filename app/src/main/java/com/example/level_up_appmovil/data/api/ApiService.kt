package com.example.level_up_appmovil.data.api

import com.example.level_up_appmovil.data.api.model.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 1. Interfaz que define los endpoints (las URL a las que llamamos)
interface ApiService {
    @GET("products") // Endpoint de FakeStoreAPI
    suspend fun getProducts(): List<Product>
}

// 2. Objeto Singleton para crear la conexión (Cliente Retrofit)
object RetrofitClient {
    private const val BASE_URL = "https://fakestoreapi.com/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}