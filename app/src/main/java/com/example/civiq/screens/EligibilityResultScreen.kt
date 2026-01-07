package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EligibilityResultScreen(navController: NavController) {
    // 1. Define the Questions List
    val questions = remember {
        listOf(
            "Is your business registered in the state?",
            "Has your business been operating for at least 1 year?",
            "Is your annual revenue less than $5 Million?",
            "Do you have a valid business license?"
        )
    }

    // 2. Define State Variables
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Boolean?>(null) }
    var isEligible by remember { mutableStateOf(true) } // Assume eligible until proven otherwise
    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eligibility Check") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showResult) {
                // --- RESULT VIEW ---
                Spacer(modifier = Modifier.height(40.dp))
                Icon(
                    imageVector = if (isEligible) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isEligible) Color(0xFF4CAF50) else Color(0xFFFFA000),
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = if (isEligible) "You are Eligible!" else "You are NOT Eligible",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isEligible) Color(0xFF4CAF50) else Color(0xFFFFA000)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (isEligible)
                        "You meet all the requirements for the Small Business Grant."
                    else
                        "Unfortunately, you do not meet one or more criteria.",
                    color = Color.Gray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        if (isEligible) navController.navigate("application_form")
                        else navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CiviqBluePrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEligible) "Proceed to Application" else "Back to Details")
                }

            } else {
                // --- QUESTION VIEW ---
                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1) / questions.size.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = questions[currentQuestionIndex],
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Yes Option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedOption == true),
                            onClick = { selectedOption = true }
                        )
                        .background(
                            if (selectedOption == true) CiviqBluePrimary.copy(alpha = 0.1f) else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedOption == true),
                        onClick = { selectedOption = true }
                    )
                    Text("Yes", modifier = Modifier.padding(start = 16.dp))
                }

                // No Option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedOption == false),
                            onClick = { selectedOption = false }
                        )
                        .background(
                            if (selectedOption == false) CiviqBluePrimary.copy(alpha = 0.1f) else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedOption == false),
                        onClick = { selectedOption = false }
                    )
                    Text("No", modifier = Modifier.padding(start = 16.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                // Next Button
                Button(
                    onClick = {
                        // Check logic: if answer is NO, user is ineligible
                        if (selectedOption == false) {
                            isEligible = false
                        }

                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedOption = null // Reset selection for next question
                        } else {
                            showResult = true
                        }
                    },
                    enabled = selectedOption != null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = CiviqBluePrimary)
                ) {
                    Text(if (currentQuestionIndex < questions.size - 1) "Next" else "Finish")
                }
            }
        }
    }
}