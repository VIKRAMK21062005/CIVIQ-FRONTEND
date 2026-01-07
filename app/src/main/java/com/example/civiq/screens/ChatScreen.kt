package com.example.civiq.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.civiq.ui.theme.CiviqBluePrimary
import com.example.civiq.ui.theme.CiviqBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Data model for a single chat message
data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String = "Now"
)

@Composable
fun ChatScreen(navController: NavController) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Add a welcome message when screen loads
    LaunchedEffect(Unit) {
        if (messages.isEmpty()) {
            messages.add(ChatMessage("Hello! I am CIVIQ AI. How can I help you with government services today?", false))
        }
    }

    // Auto-scroll to bottom when new message arrives
    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CiviqBackground)
    ) {
        // 1. Chat Header
        Surface(
            shadowElevation = 4.dp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(CiviqBluePrimary.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.SmartToy, null, tint = CiviqBluePrimary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("CIVIQ Assistant", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Always Active", fontSize = 12.sp, color = Color(0xFF4CAF50))
                }
            }
        }

        // 2. Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message)
            }
        }

        // 3. Input Area
        Surface(
            shadowElevation = 8.dp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Type a question...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CiviqBluePrimary,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            // 1. Add User Message
                            val userMsg = messageText
                            messages.add(ChatMessage(userMsg, true))
                            messageText = ""

                            // 2. Simulate AI Reply (Mock)
                            coroutineScope.launch {
                                delay(1000) // Fake "thinking" delay
                                val reply = getMockResponse(userMsg)
                                messages.add(ChatMessage(reply, false))
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(CiviqBluePrimary, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (message.isUser) CiviqBluePrimary else Color.White,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 0.dp,
                bottomEnd = if (message.isUser) 0.dp else 16.dp
            ),
            shadowElevation = 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (message.isUser) Color.White else Color.Black,
                fontSize = 15.sp
            )
        }
    }
}

// Simple Mock Logic to make the bot seem "intelligent" without a backend
fun getMockResponse(input: String): String {
    val query = input.lowercase()
    return when {
        query.contains("hello") || query.contains("hi") -> "Hello! How can I assist you with government services?"
        query.contains("document") || query.contains("require") -> "Most services require a valid ID, proof of address, and recent tax returns."
        query.contains("time") || query.contains("long") -> "Processing usually takes 5-7 business days."
        query.contains("status") -> "You can check your application status in the Profile tab."
        query.contains("grant") || query.contains("money") -> "Small Business Grants are available. Check the 'Services' tab for eligibility."
        else -> "I'm not sure about that details. Please check the Services catalog or visit the nearest center."
    }
}