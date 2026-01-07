package com.example.civiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// IMPORTS
import com.example.civiq.screens.HomeScreen
import com.example.civiq.screens.ProfileScreen
import com.example.civiq.screens.ServicesCatalogScreen
import com.example.civiq.screens.ServiceDetailScreen
import com.example.civiq.screens.ChatScreen
import com.example.civiq.screens.RegisterScreen
import com.example.civiq.screens.LoginScreen
import com.example.civiq.screens.ApplicationFormScreen
import com.example.civiq.screens.DiscoverScreen
import com.example.civiq.screens.EligibilityResultScreen // Added Missing Import
import com.example.civiq.ui.theme.CiviqTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CiviqTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    // Bottom Navigation Items
    val items = listOf(
        BottomNavItem("Home", "home", Icons.Default.Home),
        BottomNavItem("Discover", "discover", Icons.Default.Search),
        BottomNavItem("Services", "services_catalog", Icons.Default.GridView),
        BottomNavItem("Chat", "chat", Icons.Default.Chat),
        BottomNavItem("Profile", "profile", Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            // Hide Bottom Bar on Login screen
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            // Hide bottom bar on login, register, AND form screens for cleaner UI
            val showBottomBar = currentRoute !in listOf("login", "register", "application_form", "eligibility_result")

            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login", // Start at Login
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- AUTH ---
            composable("login") {
                LoginScreen(navController)
            }

            composable("register") {
                RegisterScreen(navController)
            }

            // --- MAIN APP ---
            composable("home") {
                HomeScreen(navController)
            }

            composable("discover") {
                DiscoverScreen(navController)
            }

            composable("services_catalog") {
                ServicesCatalogScreen(navController)
            }

            composable("chat") {
                ChatScreen(navController)
            }

            composable("profile") {
                ProfileScreen(navController)
            }

            // Service Details (Requires ID)
            composable("service_detail/{serviceId}") { backStackEntry ->
                val serviceId = backStackEntry.arguments?.getString("serviceId") ?: ""
                ServiceDetailScreen(navController, serviceId)
            }

            // Application Form
            composable("application_form") {
                ApplicationFormScreen(navController)
            }

            // Eligibility Result (Added this route so the button works)
            composable("eligibility_result") {
                EligibilityResultScreen(navController)
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)