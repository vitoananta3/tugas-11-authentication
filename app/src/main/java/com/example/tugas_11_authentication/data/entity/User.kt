package com.example.tugas_11_authentication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val password: String, // In production, this should be hashed
    val fullName: String,
    val dateOfBirth: Date,
    val createdAt: Date = Date(),
    val isLoggedIn: Boolean = false
)