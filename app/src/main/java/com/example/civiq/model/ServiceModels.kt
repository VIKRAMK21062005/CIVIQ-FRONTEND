package com.example.civiq.model

data class ServiceItem(
    val id: String,
    val title: String,
    val department: String,
    val category: String,
    val processing_time: String,
    val fee: String,
    val description: String,
    val icon_url: String? = null
)

data class ApplicationRequest(
    val service_id: String,
    val applicant_name: String,
    val annual_income: Int,
    val business_name: String? = null
)

data class ApplicationResponse(
    val id: Int,
    val status: String,
    val message: String? = null
)