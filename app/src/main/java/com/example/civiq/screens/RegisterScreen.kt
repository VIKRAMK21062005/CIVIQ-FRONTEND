package com.example.civiq.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.civiq.utils.SessionManager
import com.example.civiq.viewmodel.AuthViewModel

// --- PRIVATE COLORS ---
private val DarkBlueStart = Color(0xFF1565C0)
private val DarkBlueEnd = Color(0xFF051933)
private val InputGray = Color(0xFFF8F9FA)
private val ButtonBlue = Color(0xFF1E3A8A)
private val CheckGreen = Color(0xFF10B981)

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    // --- UI State ---
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val registerState by authViewModel.registerState.collectAsState()

    // --- Backend Logic ---
    LaunchedEffect(registerState) {
        registerState?.onSuccess { token ->
            isLoading = false
            SessionManager.saveToken(context, token)
            SessionManager.saveUserName(context, fullName) // Save Name for Home Screen
            Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()

            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
        registerState?.onFailure { error ->
            isLoading = false
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DarkBlueStart, DarkBlueEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // 1. Header Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFF42A5F5),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("CIVIQ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Government Services Portal", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Back to Login Link
            Row(
                modifier = Modifier.clickable { navController.popBackStack() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Back to Login", color = Color.White, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Create Account", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Join CIVIQ to access personalized government services",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. The White Form Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    // Full Name
                    InputLabel("Full Name")
                    CustomTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = "John Doe"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    InputLabel("Email Address")
                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "john@example.com",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    InputLabel("Password")
                    CustomPasswordField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Create a password",
                        isVisible = passwordVisible,
                        onToggle = { passwordVisible = !passwordVisible }
                    )
                    Text(
                        "Must be at least 8 characters long",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password
                    InputLabel("Confirm Password")
                    CustomPasswordField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Confirm your password",
                        isVisible = confirmPasswordVisible,
                        onToggle = { confirmPasswordVisible = !confirmPasswordVisible }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Create Account Button (With Logic)
                    Button(
                        onClick = {
                            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                                if (password == confirmPassword) {
                                    if (password.length >= 8) {
                                        isLoading = true
                                        // REAL API CALL HERE:
                                        authViewModel.registerUser(fullName, email, password)
                                    } else {
                                        Toast.makeText(context, "Password must be 8+ chars", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Create Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Features Grid
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeatureItem(modifier = Modifier.weight(1f), "Instant Verification")
                            FeatureItem(modifier = Modifier.weight(1f), "Secure Data")
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            FeatureItem(modifier = Modifier.weight(1f), "Official Portal")
                            FeatureItem(modifier = Modifier.weight(1f), "24/7 Support")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer Text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account? ", color = Color.White.copy(alpha = 0.7f))
                Text(
                    "Sign In",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "By continuing, you agree to our Terms of Service and Privacy Policy",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- HELPER COMPONENTS ---

@Composable
private fun InputLabel(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color(0xFF333333),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = InputGray,
            unfocusedContainerColor = InputGray,
            disabledContainerColor = InputGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
private fun CustomPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = InputGray,
            unfocusedContainerColor = InputGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = onToggle) {
                Icon(imageVector = image, contentDescription = null, tint = Color.Gray)
            }
        }
    )
}

@Composable
private fun FeatureItem(modifier: Modifier = Modifier, text: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = CheckGreen,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 12.sp, color = Color.Gray)
    }
}