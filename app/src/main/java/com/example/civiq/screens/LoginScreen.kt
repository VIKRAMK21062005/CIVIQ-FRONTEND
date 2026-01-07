package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.civiq.components.CiviqTextField
import com.example.civiq.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(CiviqBluePrimary, CiviqDarkBlue))
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Icon(Icons.Default.Security, null, tint = Color.White, modifier = Modifier.size(50.dp))
            Text("CIVIQ", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Government Services Portal", color = Color.White.copy(0.8f))

            Spacer(modifier = Modifier.height(32.dp))

            // White Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Welcome Back", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = CiviqTextDark)
                    Spacer(modifier = Modifier.height(16.dp))

                    CiviqTextField("Email", "user@example.com", Icons.Default.Email, email, { email = it })
                    CiviqTextField("Password", "********", Icons.Default.Lock, password, { password = it }, true)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CiviqBluePrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Sign In", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Don't have an account? ", color = Color.White)
                Text(
                    "Create Account",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate("register") }
                )
            }
        }
    }
}