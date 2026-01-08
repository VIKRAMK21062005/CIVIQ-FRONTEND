package com.example.civiq.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.civiq.utils.SessionManager

// --- PRIVATE COLORS ---
private val CiviqBackground = Color(0xFFF5F5F5)
private val CiviqBluePrimary = Color(0xFF1565C0)
private val CiviqGreen = Color(0xFF4CAF50)

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userName = SessionManager.getUserName(context)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CiviqBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // 1. Top Bar (Custom for Profile)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = CiviqBluePrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("CIVIQ", fontWeight = FontWeight.Bold, color = CiviqBluePrimary)
                    Text("OFFICIAL PORTAL", fontSize = 10.sp, color = Color.Gray)
                }
            }
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. User Info Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(CiviqBluePrimary),
                contentAlignment = Alignment.Center
            ) {
                val initials = if (userName.isNotEmpty()) userName.take(2).uppercase() else "GU"
                Text(initials, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = CiviqGreen,
                    modifier = Modifier.align(Alignment.BottomEnd).size(24.dp).background(Color.White, CircleShape).padding(2.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(userName, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = Color(0xFFE3F2FD), shape = RoundedCornerShape(4.dp)) {
                        Text(" VERIFIED ", fontSize = 10.sp, color = CiviqBluePrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                    }
                }
                Text("user@example.com", color = Color.Gray, fontSize = 14.sp) // Replace with dynamic email if available

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { /* Edit Profile */ },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(36.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                ) {
                    Text("EDIT PROFILE", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Profile Strength Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Profile Strength", fontWeight = FontWeight.Bold)
                        Text(
                            "Complete your profile for better service\naccess",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Text("67%", color = CiviqBluePrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { 0.67f },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                    color = CiviqBluePrimary,
                    trackColor = Color(0xFFE0E0E0),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SuggestionChip(onClick = {}, label = { Text("+ Add Address") })
                    SuggestionChip(onClick = {}, label = { Text("+ Add Contact") })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard(
                icon = Icons.Default.Description,
                value = "12",
                label = "APPLICATIONS",
                color = Color(0xFFE3F2FD),
                iconTint = CiviqBluePrimary,
                modifier = Modifier.weight(1f)
            )
            ProfileStatCard(
                icon = Icons.Default.AccessTime,
                value = "8.5h",
                label = "TIME\nSAVED",
                color = Color(0xFFE8F5E9),
                iconTint = CiviqGreen,
                modifier = Modifier.weight(1f)
            )
            ProfileStatCard(
                icon = Icons.Default.TrendingUp,
                value = "7",
                label = "SERVICES\nUSED",
                color = Color(0xFFF3E5F5),
                iconTint = Color(0xFF9C27B0),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Account Verification Grid
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Account Verification", fontWeight = FontWeight.Bold)
                    Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(4.dp)) {
                        Text(
                            " SECURE ",
                            fontSize = 10.sp,
                            color = CiviqGreen,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    VerificationItem(icon = Icons.Default.Email, title = "Email", status = "Verified", isVerified = true, modifier = Modifier.weight(1f))
                    VerificationItem(icon = Icons.Default.Phone, title = "Phone", status = "Verified", isVerified = true, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    VerificationItem(icon = Icons.Default.Badge, title = "ID", status = "Verified", isVerified = true, modifier = Modifier.weight(1f))
                    VerificationItem(icon = Icons.Default.Home, title = "Address", status = "Pending", isVerified = false, modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 6. Recommended for You
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFF9C27B0))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recommended for You", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))

                RecommendedItem(title = "Tax Filing Deadline", subtitle = "File before April 15", icon = Icons.Default.Event, isUrgent = true)
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.2f))
                RecommendedItem(title = "License Renewal Due", subtitle = "Expires in 2 months", icon = Icons.Default.Update, isUrgent = false)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 7. Recent Activity List (ADDED HERE)
        Text("Recent Activity", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ActivityRow("Passport Renewal", "2 hours ago", Color(0xFF2196F3))
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.2f))
                ActivityRow("Property Tax Payment", "Yesterday", CiviqGreen)
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.2f))
                ActivityRow("Vehicle Registration", "3 days ago", Color(0xFFFFC107))
            }
        }

        // Add Logout/Options at the very bottom if needed
        Spacer(modifier = Modifier.height(16.dp))
        ProfileOptions(onLogout = {
            SessionManager.clearSession(context)
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        })

        Spacer(modifier = Modifier.height(80.dp)) // Bottom padding
    }
}

// --- HELPER COMPOSABLES ---

@Composable
private fun ProfileStatCard(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.height(110.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconTint, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                label,
                fontSize = 10.sp,
                color = Color.Gray,
                lineHeight = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun VerificationItem(
    icon: ImageVector,
    title: String,
    status: String,
    isVerified: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color(0xFFF9F9F9), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(if(isVerified) Color(0xFFE8F5E9) else Color(0xFFEEEEEE), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = if(isVerified) CiviqGreen else Color.Gray, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(status, fontSize = 10.sp, color = if(isVerified) CiviqGreen else Color.Gray)
        }
        Spacer(modifier = Modifier.weight(1f))
        if(isVerified) {
            Icon(Icons.Default.CheckCircle, null, tint = CiviqGreen, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun RecommendedItem(title: String, subtitle: String, icon: ImageVector, isUrgent: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontWeight = FontWeight.Medium)
                if (isUrgent) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.Bolt, null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                }
            }
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun ActivityRow(title: String, time: String, dotColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(8.dp).background(dotColor, CircleShape))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.LightGray, modifier = Modifier.size(14.dp))
    }
}

@Composable
fun ProfileOptions(onLogout: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            ProfileOptionItem("Edit Profile", Icons.Default.Edit) {}
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
            ProfileOptionItem("Settings", Icons.Default.Settings) {}
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
            ProfileOptionItem("Help & Support", Icons.Default.Help) {}
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
            ProfileOptionItem("Logout", Icons.Default.ExitToApp, danger = true, onClick = onLogout)
        }
    }
}

@Composable
fun ProfileOptionItem(
    title: String,
    icon: ImageVector,
    danger: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
            color = if (danger) Color.Red else Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}