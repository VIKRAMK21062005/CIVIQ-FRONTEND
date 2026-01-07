package com.example.civiq.model

// Matches what ServicesCatalogScreen expects
data class ServiceItem(
    val id: String,
    val title: String,
    val department: String, // Renamed from 'category' to fix 'Unresolved reference: department'
    val description: String,
    val iconUrl: String? = null
)

// Matches what ServiceDetailScreen expects
data class ServiceDetail(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val requirements: List<String>,
    val processing_time: String, // Renamed from 'timeEstimate' to fix 'Unresolved reference'
    val fee: String,
    val mode: String,
    val overview: String = "" // Added default to prevent errors
)

// Matches what ApplicationViewModel sends
data class ApplicationRequest(
    val serviceId: String,      // camelCase
    val applicantName: String,  // camelCase
    val businessName: String,   // camelCase
    val annualRevenue: Double   // camelCase
)

data class ApplicationResponse(
    val success: Boolean,
    val applicationId: String?,
    val message: String
)