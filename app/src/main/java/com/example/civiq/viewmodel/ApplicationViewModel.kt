package com.example.civiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civiq.model.ApplicationRequest
import com.example.civiq.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApplicationViewModel : ViewModel() {

    // State to track the submission result (Success message or Error)
    private val _submissionState = MutableStateFlow<Result<String>?>(null)
    val submissionState: StateFlow<Result<String>?> = _submissionState

    fun submitApplication(token: String, serviceId: String, name: String, income: String, business: String) {
        viewModelScope.launch {
            try {
                // Validate and Convert Input
                val incomeInt = income.toIntOrNull() ?: 0
                val businessValue = if (business.isBlank()) null else business

                // Create the Request Object
                val request = ApplicationRequest(
                    service_id = serviceId,
                    applicant_name = name,
                    annual_income = incomeInt,
                    business_name = businessValue
                )

                // Format Token
                val formattedToken = if (token.startsWith("Bearer ")) token else "Bearer $token"

                // API Call
                val response = RetrofitInstance.api.submitApplication(formattedToken, request)

                // Handle Response
                if (response.isSuccessful && response.body() != null) {
                    val appId = response.body()!!.id
                    _submissionState.value = Result.success("Application #$appId Submitted Successfully!")
                } else {
                    _submissionState.value = Result.failure(Exception("Submission Failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                _submissionState.value = Result.failure(e)
            }
        }
    }
}