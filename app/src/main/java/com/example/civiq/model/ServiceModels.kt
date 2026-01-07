package com.example.civiq.model

// Matches your Pydantic schema
data class ServiceItem(
    val id: String,
    val title: String,
    val department: String,
    val category: String,
    val processing_time: String, // Ensure variable names match your JSON (snake_case vs camelCase)
    val fee: String,
    val description: String,
    val icon_url: String? = null
)