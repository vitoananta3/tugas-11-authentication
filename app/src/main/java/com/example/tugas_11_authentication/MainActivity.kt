package com.example.tugas_11_authentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tugas_11_authentication.navigation.AppNavigation
import com.example.tugas_11_authentication.navigation.Screen
import com.example.tugas_11_authentication.ui.theme.Tugas11authenticationTheme
import com.example.tugas_11_authentication.viewmodel.AuthViewModel
import com.example.tugas_11_authentication.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        sharedPreferences = getSharedPreferences("workfun_prefs", Context.MODE_PRIVATE)
        
        setContent {
            Tugas11authenticationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WorkFunApp()
                }
            }
        }
    }
    
    @Composable
    fun WorkFunApp() {
        val navController = rememberNavController()
        val viewModelFactory = ViewModelFactory(this@MainActivity)
        val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
        
        val currentUser by authViewModel.currentUser.collectAsState()
        var startDestination by remember { mutableStateOf<String?>(null) }
        
        // Determine start destination based on onboarding and auth state
        LaunchedEffect(Unit) {
            delay(100) // Small delay to ensure ViewModel is initialized
            val hasSeenOnboarding = sharedPreferences.getBoolean("has_seen_onboarding", false)
            
            startDestination = when {
                !hasSeenOnboarding -> Screen.Onboarding.route
                currentUser != null -> Screen.Home.route
                else -> Screen.Login.route
            }
        }
        
        // Mark onboarding as seen when user finishes it
        LaunchedEffect(navController.currentDestination?.route) {
            if (navController.currentDestination?.route == Screen.Login.route) {
                sharedPreferences.edit().putBoolean("has_seen_onboarding", true).apply()
            }
        }
        
        startDestination?.let { destination ->
            AppNavigation(
                navController = navController,
                authViewModel = authViewModel,
                startDestination = destination
            )
        }
    }
}