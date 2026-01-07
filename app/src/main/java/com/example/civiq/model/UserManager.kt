package com.example.civiq.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object UserManager {
    // This holds the user's name globally
    var userName by mutableStateOf("Guest User")
    var userEmail by mutableStateOf("guest@example.com")
    var userPhone by mutableStateOf("+1 (555) 000-0000")

    fun login(name: String, email: String) {
        userName = name
        userEmail = email
    }
}