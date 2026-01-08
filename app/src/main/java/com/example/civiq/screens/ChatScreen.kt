package com.example.civiq.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.civiq.ui.theme.CiviqBackground
import com.example.civiq.ui.theme.CiviqBluePrimary
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ----------------------------------------------------------------
// 1. DATA LAYER (Networking)
// ----------------------------------------------------------------

// Data models
data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String = getCurrentTime(),
    val isTyping: Boolean = false // To show "..." animation
)

data class ApiRequest(val query: String)
data class ApiResponse(val response: String)

// API Interface connecting to your Python FastAPI Backend
interface CiviqApiService {
    @POST("/chat") // Assumes your Python backend has this endpoint
    suspend fun sendMessage(@Body request: ApiRequest): ApiResponse
}

// Singleton Retrofit Instance
object RetrofitClient {
    // 10.0.2.2 is the localhost alias for the Android Emulator
    // If testing on a real device, use your PC's local IP (e.g., 192.168.1.X)
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val api: CiviqApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CiviqApiService::class.java)
    }
}

fun getCurrentTime(): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
}

// ----------------------------------------------------------------
// 2. VIEWMODEL (Business Logic)
// ----------------------------------------------------------------

class ChatViewModel : ViewModel() {
    // State backed by snapshotFlow
    private val _messages = mutableStateListOf<ChatMessage>()
    val messages: List<ChatMessage> = _messages

    var userInput by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    init {
        // Initial Welcome Message
        _messages.add(
            ChatMessage(
                "Hello! I am CIVIQ. How can I assist you with government schemes today?",
                isUser = false
            )
        )
    }

    fun sendMessage() {
        val currentMsg = userInput.trim()
        if (currentMsg.isBlank()) return

        // 1. Add User Message immediately
        _messages.add(ChatMessage(currentMsg, isUser = true))
        userInput = ""
        isLoading = true

        viewModelScope.launch {
            try {
                // 2. Call the Real Backend
                // val response = RetrofitClient.api.sendMessage(ApiRequest(currentMsg))
                // _messages.add(ChatMessage(response.response, isUser = false))

                // --- MOCK MODE (Remove this block when Python backend is running) ---
                kotlinx.coroutines.delay(1000) // Simulate network delay
                val mockResponse = getSmartMockResponse(currentMsg)
                _messages.add(ChatMessage(mockResponse, isUser = false))
                // -------------------------------------------------------------------

            } catch (e: Exception) {
                _messages.add(ChatMessage("Error: ${e.message}. Is the backend running?", isUser = false))
                Log.e("ChatError", "API Failed", e)
            } finally {
                isLoading = false
            }
        }
    }

    // Retaining your logic logic for fallback
    private fun getSmartMockResponse(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("hello") -> "Hello! I can help you find government grants and services."
            q.contains("document") -> "Typically, you will need an Aadhar card, Proof of Address, and Income Certificate."
            q.contains("status") -> "You can track your application ID in the 'Profile' section of the app."
            else -> "I can help with that. Could you specify which government department this is related to?"
        }
    }
}

// ----------------------------------------------------------------
// 3. UI LAYER (The Screen)
// ----------------------------------------------------------------

@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = viewModel() // Inject ViewModel
) {
    val listState = rememberLazyListState()

    // Auto-scroll when messages change
    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.isNotEmpty()) {
            listState.animateScrollToItem(viewModel.messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CiviqBackground)
    ) {
        // --- Header ---
        ChatHeader()

        // --- Messages List ---
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.messages) { message ->
                ChatBubble(message)
            }

            // Show a "Thinking" bubble if loading
            if (viewModel.isLoading) {
                item {
                    ThinkingBubble()
                }
            }
        }

        // --- Input Area ---
        ChatInputArea(
            inputValue = viewModel.userInput,
            onValueChange = { viewModel.userInput = it },
            onSend = { viewModel.sendMessage() },
            isLoading = viewModel.isLoading
        )
    }
}

@Composable
fun ChatHeader() {
    Surface(
        shadowElevation = 8.dp,
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(CiviqBluePrimary, Color(0xFF42A5F5))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "CIVIQ Assistant",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Green, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Online",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.isUser

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(end = if(isUser) 0.dp else 40.dp)
        ) {
            // Bot Avatar (Only for bot messages)
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.LightGray.copy(alpha = 0.4f), CircleShape)
                        .border(1.dp, CiviqBluePrimary.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.SmartToy, null, modifier = Modifier.size(20.dp), tint = CiviqBluePrimary)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            // The Message Bubble
            Column(horizontalAlignment = if (isUser) Alignment.End else Alignment.Start) {
                Surface(
                    shape = if (isUser) {
                        RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp)
                    } else {
                        RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp)
                    },
                    color = if (isUser) CiviqBluePrimary else Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.widthIn(max = 280.dp)
                ) {
                    Text(
                        text = message.text,
                        modifier = Modifier.padding(16.dp),
                        color = if (isUser) Color.White else Color(0xFF333333),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp)
                    )
                }

                // Timestamp
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ThinkingBubble() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(40.dp)) // Align with bot text
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Text(
                text = "typing...",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.Gray,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ChatInputArea(
    inputValue: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isLoading: Boolean
) {
    Surface(
        shadowElevation = 12.dp,
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
                value = inputValue,
                onValueChange = onValueChange,
                placeholder = { Text("Ask about schemes...") },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 50.dp, max = 100.dp), // Grows with text
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CiviqBluePrimary,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color(0xFFFAFAFA),
                    unfocusedContainerColor = Color(0xFFFAFAFA)
                ),
                maxLines = 4
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Send Button
            IconButton(
                onClick = onSend,
                enabled = inputValue.isNotBlank() && !isLoading,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        if (inputValue.isNotBlank()) CiviqBluePrimary else Color.LightGray,
                        CircleShape
                    )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.offset(x = 2.dp) // Optical centering
                    )
                }
            }
        }
    }
}