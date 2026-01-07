package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.civiq.ui.theme.CiviqBluePrimary
import com.example.civiq.ui.theme.CiviqBackground

@Composable
fun DiscoverScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    // Dummy Data for Nearby Centers
    val centers = listOf(
        CenterItem("City Hall Main Office", "0.8 km", "Open • Closes 5 PM", "Downtown"),
        CenterItem("Civic Center Library", "1.2 km", "Open • Closes 8 PM", "Westside"),
        CenterItem("Department of Motor Vehicles", "2.5 km", "Closed • Opens 9 AM", "North Hills"),
        CenterItem("Community Health Clinic", "3.0 km", "Open • Closes 4 PM", "Eastside")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CiviqBackground)
            .padding(16.dp)
    ) {
        // 1. Header & Search
        Text("Discover", fontWeight = FontWeight.Bold, fontSize = 28.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search services, centers...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.FilterList, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Map View Placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Light Blue Map placeholder
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Map,
                        contentDescription = "Map",
                        tint = CiviqBluePrimary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Map View", color = CiviqBluePrimary, fontWeight = FontWeight.Bold)
                    Text("View nearby centers on map", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Nearby List
        Text("Nearby Centers", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp), // Space for bottom nav
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(centers) { center ->
                CenterCard(center)
            }
        }
    }
}

// --- Helper Components ---

data class CenterItem(val name: String, val distance: String, val status: String, val location: String)

@Composable
fun CenterCard(center: CenterItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Box
            Surface(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = CiviqBluePrimary)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Info
            Column(modifier = Modifier.weight(1f)) {
                Text(center.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(center.location, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(center.distance, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text("  •  ", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        center.status,
                        fontSize = 12.sp,
                        color = if (center.status.contains("Open")) Color(0xFF4CAF50) else Color.Red
                    )
                }
            }
        }
    }
}