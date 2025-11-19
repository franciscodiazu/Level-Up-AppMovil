package com.example.level_up_appmovil.data.db

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.level_up_appmovil.data.api.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "level_up_database"
                )
                    .addCallback(AppDatabaseCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.userDao())
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(userDao: UserDao) {
            // Usuario ADMIN
            userDao.insert(
                User(
                    id = 0,
                    email = "admin@duocuc.cl",
                    pass = "123456",
                    birthDate = LocalDate.of(1990, 1, 1),
                    isDuocMember = true,
                    photoUri = null
                )
            )

            // Usuario CLIENTE
            userDao.insert(
                User(
                    id = 0,
                    email = "cliente@duocuc.cl",
                    pass = "123456",
                    birthDate = LocalDate.of(2000, 5, 20),
                    isDuocMember = false,
                    photoUri = null
                )
            )
        }
    }
}