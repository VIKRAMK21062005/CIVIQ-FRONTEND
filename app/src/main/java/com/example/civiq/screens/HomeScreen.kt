package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light Gray Background
            .verticalScroll(scrollState)
    ) {
        // 1. Header Section (Dark Blue)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D1B2A)) // Civiq Dark Blue
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Welcome back,",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "John", // Hardcoded User Name
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    // Profile Icon / Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Stats Row (Floating Card Effect) [cite: 1432-1440]
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(value = "12", label = "Applications", icon = Icons.Default.Description)
                        StatItem(value = "8.0h", label = "Time Saved", icon = Icons.Default.AccessTime)
                        StatItem(value = "7", label = "Services Used", icon = Icons.Default.Category)
                    }
                }
            }
        }

        // 3. AI Eligibility Banner [cite: 1455-1461]
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Light Blue
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { /* Navigate to Eligibility */ }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "AI Eligibility Checker",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                    Text(
                        text = "Discover services you qualify for",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        // 4. Quick Access Grid [cite: 1442-1453]
        Text(
            text = "Quick Access",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Using standard icons mapped to your PDF design
            QuickAccessItem("Services", Icons.Default.GridView) { navController.navigate("services_catalog") }
            QuickAccessItem("Documents", Icons.Default.Folder) { /* TODO */ }
            QuickAccessItem("Assistant", Icons.Default.Chat) { /* TODO */ }
            QuickAccessItem("Account", Icons.Default.ManageAccounts) { /* TODO */ }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 5. Recent Activity List
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Recent Activity", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "View All", color = Color(0xFF1565C0), fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Activity Item 1: Passport [cite: 1465]
        ActivityItem(
            title = "Passport Renewal",
            status = "In Progress",
            date = "Today, 10:23 AM",
            statusColor = Color(0xFFFFA000), // Amber
            icon = Icons.Default.Flight
        )

        // Activity Item 2: Property Tax [cite: 1472]
        ActivityItem(
            title = "Property Tax Payment",
            status = "Completed",
            date = "Yesterday",
            statusColor = Color(0xFF4CAF50), // Green
            icon = Icons.Default.Home
        )

        // Activity Item 3: Vehicle Registration [cite: 1476]
        ActivityItem(
            title = "Vehicle Registration",
            status = "Pending Review",
            date = "Oct 24, 2023",
            statusColor = Color.Gray,
            icon = Icons.Default.DirectionsCar
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// --- Helper Composables ---

@Composable
private fun StatItem(value: String, label: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun QuickAccessItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp,
            modifier = Modifier.size(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = label, tint = Color(0xFF1565C0))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ActivityItem(title: String, status: String, date: String, statusColor: Color, icon: ImageVector) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = date, fontSize = 12.sp, color = Color.Gray)
            }

            // Status Badge
            Surface(
                color = statusColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = status,
                    color = statusColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}