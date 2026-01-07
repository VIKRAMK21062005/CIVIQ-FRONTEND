package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// --- Colors (Matched to your Dashboard) ---
// Defined locally so this file works immediately without import errors
private val CiviqBackground = Color(0xFFF5F5F5)
private val CiviqBluePrimary = Color(0xFF1565C0)
private val CiviqTextDark = Color(0xFF0D1B2A)

@Composable
fun ProfileScreen(
    navController: NavController
) {
    // 1. Add Scroll State so content doesn't get cut off on small screens
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CiviqBackground)
            .padding(16.dp)
            .verticalScroll(scrollState) // Enable scrolling
    ) {

        ProfileHeader()
        Spacer(modifier = Modifier.height(16.dp))

        ProfileStrengthCard()
        Spacer(modifier = Modifier.height(16.dp))

        VerificationCard()
        Spacer(modifier = Modifier.height(16.dp))

        ProfileOptions()

        // Add extra space at bottom for the navigation bar
        Spacer(modifier = Modifier.height(80.dp))
    }
}

/* ---------------- PROFILE HEADER ---------------- */

@Composable
fun ProfileHeader() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(CiviqBluePrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JD",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("John Doe", fontWeight = FontWeight.Bold, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified)
                Text(
                    "+91 98765 43210",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // 'Verified' usually requires Extended Icons.
            // Using CheckCircle as a safe fallback if Extended isn't synced.
            AssistChip(
                onClick = {},
                label = { Text("Verified") },
                leadingIcon = {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50) // Green
                    )
                }
            )
        }
    }
}

/* ---------------- PROFILE STRENGTH ---------------- */

@Composable
fun ProfileStrengthCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Profile Strength", fontWeight = FontWeight.Bold)
                Text("67%", color = CiviqBluePrimary, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = 0.67f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = CiviqBluePrimary,
                trackColor = CiviqBackground
            )


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Complete your profile for better service access",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SuggestionChip(onClick = {}, label = { Text("Add Address") })
                SuggestionChip(onClick = {}, label = { Text("Add Contact") })
            }
        }
    }
}

/* ---------------- VERIFICATION ---------------- */

@Composable
fun VerificationCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Account Verification", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            VerificationItem("Email", true)
            VerificationItem("Phone", true)
            VerificationItem("ID", true)
            VerificationItem("Address", false)
        }
    }
}

@Composable
fun VerificationItem(title: String, verified: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium)
        Icon(
            imageVector = if (verified) Icons.Default.CheckCircle else Icons.Default.Schedule,
            contentDescription = null,
            tint = if (verified) Color(0xFF4CAF50) else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

/* ---------------- OPTIONS ---------------- */

@Composable
fun ProfileOptions() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            ProfileOptionItem("Edit Profile", Icons.Default.Edit)
            ProfileOptionItem("Security", Icons.Default.Lock)
            // Use standard icons to avoid build errors
            ProfileOptionItem("Language", Icons.Default.Info)
            // Help and Logout are often AutoMirrored in newer Compose
            ProfileOptionItem("Help & Support", Icons.AutoMirrored.Filled.Help)
            ProfileOptionItem("Logout", Icons.AutoMirrored.Filled.ExitToApp, danger = true)
        }
    }
}

@Composable
fun ProfileOptionItem(
    title: String,
    icon: ImageVector,
    danger: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (danger) Color.Red else CiviqBluePrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = if (danger) Color.Red else CiviqTextDark,
            fontWeight = FontWeight.Medium
        )
    }
}