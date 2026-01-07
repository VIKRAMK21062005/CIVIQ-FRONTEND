package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Data model representing the details from the PDF (e.g., Page 1)
data class ServiceDetail(
    val title: String,
    val category: String,
    val rating: Double,
    val description: String,
    val timeEstimate: String,
    val fee: String,
    val mode: String,
    val overview: String,
    val requirements: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Hardcoded data based on "Small Business Grant Program" from Page 1 of your PDF
    val service = ServiceDetail(
        title = "Small Business Grant Program", //
        category = "Economic Development", // [cite: 1]
        rating = 4.5, // [cite: 25]
        description = "Apply for grants to start or expand your small business.", // [cite: 4]
        timeEstimate = "6-8 Weeks", // [cite: 7]
        fee = "Free", // [cite: 9]
        mode = "Online", // [cite: 10]
        overview = "Small business grants provide non-repayable funds for qualifying businesses. Grants may be used for equipment, inventory, marketing, or operational expenses.", // [cite: 13]
        requirements = listOf(
            "Business Plan", // [cite: 16]
            "Financial Statements", // [cite: 17]
            "Business License", // [cite: 18]
            "Tax Returns (2 years)", // [cite: 19]
            "Proof of Business Ownership", // [cite: 20]
            "Grant Proposal" // [cite: 21]
        )
    )

    Scaffold(
        bottomBar = {
            BottomActionButtons(navController) // "Ask AI" and "Apply Now" buttons [cite: 22, 24]
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Light gray background
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Header Section (Dark Blue Background)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF0D1B2A)) // Dark blue from screenshots
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            // 2. Floating Card with Main Info
            // Using negative offset to pull the card up over the blue header, like in Page 1 and 16
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-100).dp) // Pull up
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Category & Rating Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFFE3F2FD), // Light blue pill
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = service.category,
                                color = Color(0xFF1565C0),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFC107), // Amber/Gold
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = service.rating.toString(),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Title
                    Text(
                        text = service.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description
                    Text(
                        text = service.description,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Stats Row (Time, Fee, Mode) [cite: 6, 8, 10]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(icon = Icons.Default.Schedule, label = "Time", value = service.timeEstimate)
                        StatItem(icon = Icons.Default.AttachMoney, label = "Fee", value = service.fee)
                        StatItem(icon = Icons.Default.Language, label = "Mode", value = service.mode)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // "Check Eligibility" Button [cite: 11]
                    Button(
                        onClick = { /* TODO: Check logic */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Check Eligibility")
                    }
                }
            }

            // 3. Overview & Requirements Section
            // We adjust padding because of the negative offset above
            Column(
                modifier = Modifier
                    .offset(y = (-80).dp)
                    .padding(horizontal = 16.dp)
            ) {
                // Overview
                Text(
                    text = "Overview", // [cite: 12]
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = service.overview,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Requirements List [cite: 15]
                Text(
                    text = "Requirements",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                service.requirements.forEach { requirement ->
                    RequirementItem(text = requirement)
                }
            }
        }
    }
}

// Helper Composable for the 3 stats (Time, Fee, Mode)
@Composable
private fun StatItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

// Helper Composable for the Requirements list
@Composable
fun RequirementItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Description, // Using a generic document icon
            contentDescription = null,
            tint = Color(0xFF1565C0),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 14.sp)
    }
}

// Bottom Bar with "Ask AI" and "Apply Now" [cite: 22, 23, 24]
// Updated Bottom Bar with Navigation logic
@Composable
fun BottomActionButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = { navController.navigate("chat") }, // Now opens Chat
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ask AI")
        }

        Button(
            onClick = { navController.navigate("application_form") }, // Now opens Application Form
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Apply Now")
        }
    }
}