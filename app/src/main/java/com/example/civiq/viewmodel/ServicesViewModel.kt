package com.example.civiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civiq.model.ServiceItem
import com.example.civiq.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Define the possible states of the UI
sealed class ServicesState {
    object Loading : ServicesState()
    data class Success(val services: List<ServiceItem>) : ServicesState()
    data class Error(val message: String) : ServicesState()
}

class ServicesViewModel : ViewModel() {

    // State for the List of Services
    private val _state = MutableStateFlow<ServicesState>(ServicesState.Loading)
    val state: StateFlow<ServicesState> = _state

    // --- MISSING PART RESTORED BELOW ---

    // State for a Single Selected Service (Detail View)
    private val _selectedService = MutableStateFlow<ServiceItem?>(null)
    val selectedService: StateFlow<ServiceItem?> = _selectedService

    // Fetch List of Services
    fun fetchServices(userToken: String) {
        viewModelScope.launch {
            _state.value = ServicesState.Loading
            try {
                val formattedToken = formatToken(userToken)
                val response = RetrofitInstance.api.getServices(formattedToken)
                _state.value = ServicesState.Success(response)
            } catch (e: Exception) {
                _state.value = ServicesState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    // Fetch Single Service Detail
    fun getServiceDetails(userToken: String, serviceId: String) {
        viewModelScope.launch {
            try {
                // Clear previous data first
                _selectedService.value = null

                val formattedToken = formatToken(userToken)
                val response = RetrofitInstance.api.getServiceDetail(formattedToken, serviceId)
                _selectedService.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun formatToken(token: String): String {
        return if (token.startsWith("Bearer ")) token else "Bearer $token"
    }
}