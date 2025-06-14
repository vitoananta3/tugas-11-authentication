package com.example.tugas_11_authentication.repository

import com.example.tugas_11_authentication.data.dao.UserDao
import com.example.tugas_11_authentication.data.entity.User
import kotlinx.coroutines.flow.Flow
import java.util.Date

class AuthRepository(private val userDao: UserDao) {

    fun getCurrentUserFlow(): Flow<User?> = userDao.getCurrentUserFlow()

    suspend fun getCurrentUser(): User? = userDao.getCurrentUser()

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val user = userDao.authenticateUser(email, password)
            if (user != null) {
                // Logout all other users first
                userDao.logoutAllUsers()
                // Login current user
                userDao.loginUser(user.id)
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Invalid email or password")
            }
        } catch (e: Exception) {
            AuthResult.Error("Login failed: ${e.message}")
        }
    }

    suspend fun register(
        email: String,
        password: String,
        fullName: String,
        dateOfBirth: Date
    ): AuthResult {
        return try {
            // Check if email already exists
            if (userDao.isEmailExists(email) > 0) {
                return AuthResult.Error("Email already exists")
            }

            val newUser = User(
                email = email,
                password = password, // In production, hash this password
                fullName = fullName,
                dateOfBirth = dateOfBirth,
                createdAt = Date(),
                isLoggedIn = false
            )

            val userId = userDao.insertUser(newUser)
            val createdUser = newUser.copy(id = userId)
            AuthResult.Success(createdUser)
        } catch (e: Exception) {
            AuthResult.Error("Registration failed: ${e.message}")
        }
    }

    suspend fun logout(): Boolean {
        return try {
            userDao.logoutAllUsers()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun isUserLoggedIn(): Boolean {
        return getCurrentUser() != null
    }
}

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}