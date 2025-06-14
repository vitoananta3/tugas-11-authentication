package com.example.tugas_11_authentication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tugas_11_authentication.data.converter.DateConverter
import com.example.tugas_11_authentication.data.dao.UserDao
import com.example.tugas_11_authentication.data.entity.User
    
@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
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
                    "workfun_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}