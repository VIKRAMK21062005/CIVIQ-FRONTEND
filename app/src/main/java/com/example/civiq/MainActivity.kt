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
import androidx.compose.ui.unit.dp // <--- Added missing import
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.civiq.screens.HomeScreen
import com.example.civiq.screens.ProfileScreen
import com.example.civiq.screens.ServicesCatalogScreen
import com.example.civiq.screens.ServiceDetailScreen
import com.example.civiq.screens.ChatScreen // Ensure this is imported
import com.example.civiq.ui.theme.CiviqTheme // <--- Added missing import

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ensure CiviqTheme is defined in ui/theme/Theme.kt
            CiviqTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("Home", "home", Icons.Default.Home),
        BottomNavItem("Discover", "discover", Icons.Default.Search),
        BottomNavItem("Services", "services_catalog", Icons.Default.GridView),
        BottomNavItem("Chat", "chat", Icons.Default.Chat),
        BottomNavItem("Profile", "profile", Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController)
            }
            composable("discover") {
                // Placeholder
                Text("Discover Screen", modifier = Modifier.padding(16.dp))
            }
            composable("services_catalog") {
                ServicesCatalogScreen(navController, userToken = "dummy_token")
            }
            composable("chat") {
                ChatScreen(navController)
            }
            composable("profile") {
                ProfileScreen(navController)
            }
            composable("service_detail/{serviceId}") { _ ->
                ServiceDetailScreen(navController)
            }
            // ... other composables ...

            composable("application_form") {
                // Ensure ApplicationFormScreen is imported
                com.example.civiq.screens.ApplicationFormScreen(navController)
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)