package com.example.civiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civiq.model.ApplicationRequest
import com.example.civiq.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApplicationViewModel : ViewModel() {

    private val _submissionStatus = MutableStateFlow<Boolean?>(null)
    val submissionStatus: StateFlow<Boolean?> = _submissionStatus

    fun submitApplication(
        serviceId: String,
        applicantName: String,
        businessName: String,
        annualRevenue: Double
    ) {
        viewModelScope.launch {
            try {
                // FIXED: Using camelCase parameter names (serviceId, etc.)
                // to match the ApplicationRequest data class
                val request = ApplicationRequest(
                    serviceId = serviceId,
                    applicantName = applicantName,
                    businessName = businessName,
                    annualRevenue = annualRevenue
                )

                val response = RetrofitClient.apiService.submitApplication(request)

                if (response.isSuccessful && response.body()?.success == true) {
                    _submissionStatus.value = true
                } else {
                    _submissionStatus.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _submissionStatus.value = false
            }
        }
    }
}