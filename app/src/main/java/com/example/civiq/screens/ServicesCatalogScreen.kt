package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.civiq.model.ServiceItem
import com.example.civiq.ui.theme.*
import com.example.civiq.viewmodel.ServicesState
import com.example.civiq.viewmodel.ServicesViewModel


@Composable
fun ServicesCatalogScreen(
    navController: NavController,
    userToken: String,
    viewModel: ServicesViewModel = viewModel()
) {
    // Trigger fetch when screen opens
    LaunchedEffect(Unit) {
        viewModel.fetchServices(userToken)
    }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CiviqBackground) // Ensure this is defined in your Color.kt
            .padding(16.dp)
    ) {
        Text(
            "Services Catalog",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = CiviqDarkBlue // Ensure this is defined in your Color.kt
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is ServicesState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CiviqBluePrimary)
                }
            }
            is ServicesState.Error -> {
                Text(
                    text = "Error: ${(state as ServicesState.Error).message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is ServicesState.Success -> {
                val services = (state as ServicesState.Success).services
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(services) { service ->
                        ServiceListCard(service) {
                            // Navigate to detail: "service_detail/123"
                            navController.navigate("service_detail/${service.id}")
                        }
                    }
                }
            }
        }
    }
}

// ... keep your imports, BUT DELETE the lines for 'Business' and 'DirectionsCar'

@Composable
fun ServiceListCard(service: ServiceItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // FIX: Using standard icons (Home and Info) instead of Extended icons
            val icon = when (service.category) {
                "Transport" -> Icons.Filled.Info // Use Filled, not Default
                "Business" -> Icons.Filled.Home
                else -> Icons.Filled.Home
            }

            // Icon Box
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(CiviqBluePrimary.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = CiviqBluePrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // ... rest of your code remains the same ...
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                // ...
            }
        }
    }
}

@Composable
fun Badge(text: String) {
    Surface(
        color = CiviqBackground,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            color = CiviqTextDark
        )
    }
}