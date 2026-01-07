package com.example.civiq.model

// These are the shared data classes used by both ViewModel and Network
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val full_name: String
)

data class LoginResponse(
    val access_token: String?,
    val token_type: String?,
    val detail: String?
)
