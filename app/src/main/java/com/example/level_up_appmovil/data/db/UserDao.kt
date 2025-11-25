package com.example.level_up_appmovil.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.level_up_appmovil.data.api.model.LocalProduct // Asegúrate de que este import sea correcto
import com.example.level_up_appmovil.data.api.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // --- Métodos de Usuario (los que ya tenías) ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun observeUserByEmail(email: String): Flow<User?>

    @Query("SELECT * FROM users WHERE email = :email AND pass = :pass LIMIT 1")
    suspend fun findUserByEmailAndPassword(email: String, pass: String): User?

    // --- NUEVOS MÉTODOS PARA PRODUCTOS (CRUD) ---

    @Query("SELECT * FROM local_products ORDER BY id DESC")
    fun getAllLocalProducts(): Flow<List<LocalProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: LocalProduct)

    @Delete
    suspend fun deleteProduct(product: LocalProduct)

    @Update
    suspend fun updateProduct(product: LocalProduct)
}
