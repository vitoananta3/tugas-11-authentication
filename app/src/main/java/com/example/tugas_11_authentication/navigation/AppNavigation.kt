package com.example.tugas_11_authentication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tugas_11_authentication.ui.screens.*
import com.example.tugas_11_authentication.viewmodel.AuthViewModel

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Booking : Screen("booking")
    object User : Screen("user")
}

data class BottomNavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home.route, Icons.Default.Home, "Home"),
    BottomNavItem(Screen.Booking.route, Icons.Default.Book, "Book"),
    BottomNavItem(Screen.User.route, Icons.Default.Person, "Profile")
)

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
    startDestination: String
) {
    val uiState by authViewModel.uiState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                uiState = uiState,
                onLogin = { email, password ->
                    authViewModel.login(email, password)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onClearError = {
                    authViewModel.clearError()
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                uiState = uiState,
                onRegister = { email, password, fullName, dateOfBirth ->
                    authViewModel.register(email, password, fullName, dateOfBirth)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onClearError = {
                    authViewModel.clearError()
                },
                onClearRegistrationSuccess = {
                    authViewModel.clearRegistrationSuccess()
                }
            )
        }

        composable(Screen.Home.route) {
            MainAppWithBottomNav(
                authViewModel = authViewModel,
                startDestination = Screen.Home.route,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Booking.route) {
            MainAppWithBottomNav(
                authViewModel = authViewModel,
                startDestination = Screen.Booking.route,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Booking.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.User.route) {
            MainAppWithBottomNav(
                authViewModel = authViewModel,
                startDestination = Screen.User.route,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.User.route) { inclusive = true }
                    }
                }
            )
        }
    }

    // Handle navigation based on authentication state
    LaunchedEffect(uiState.isLoggedIn, currentUser) {
        if (uiState.isLoggedIn && currentUser != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }
}

@Composable
fun MainAppWithBottomNav(
    authViewModel: AuthViewModel,
    startDestination: String,
    onNavigateToLogin: () -> Unit
) {
    val bottomNavController = rememberNavController()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            // Check authentication for protected routes (only User profile requires auth)
                            if (item.route == Screen.User.route && currentUser == null) {
                                onNavigateToLogin()
                            } else {
                                bottomNavController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    user = currentUser,
                    onNavigateToBook = {
                        bottomNavController.navigate(Screen.Booking.route)
                    },
                    onNavigateToProfile = {
                        if (currentUser == null) {
                            onNavigateToLogin()
                        } else {
                            bottomNavController.navigate(Screen.User.route)
                        }
                    }
                )
            }

            composable(Screen.Booking.route) {
                BookingScreen(
                    onNavigateBack = {
                        bottomNavController.navigate(Screen.Home.route)
                    }
                )
            }

            composable(Screen.User.route) {
                UserScreen(
                    user = currentUser,
                    onNavigateBack = {
                        bottomNavController.navigate(Screen.Home.route)
                    },
                    onLogout = {
                        authViewModel.logout()
                    }
                )
            }
        }
    }
}