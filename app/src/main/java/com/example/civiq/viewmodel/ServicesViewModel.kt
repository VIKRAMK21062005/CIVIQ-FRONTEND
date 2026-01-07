package com.example.civiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civiq.model.ServiceDetail
import com.example.civiq.model.ServiceItem
import com.example.civiq.network.RetrofitClient
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

    // State for a Single Selected Service (Detail View)
    // FIXED: Now uses 'ServiceDetail' instead of 'ServiceItem'
    private val _selectedService = MutableStateFlow<ServiceDetail?>(null)
    val selectedService: StateFlow<ServiceDetail?> = _selectedService

    // Fetch List of Services
    // FIXED: Renamed to 'loadServices' and removed 'userToken' to match ApiService
    // ... inside ServicesViewModel ...

    fun loadServices() {
        viewModelScope.launch {
            _state.value = ServicesState.Loading
            try {
                // Try to get data from Backend
                val response = RetrofitClient.apiService.getServices()
                _state.value = ServicesState.Success(response)
            } catch (e: Exception) {
                // FAILED TO CONNECT? Load Dummy Data instead!
                val dummyServices = listOf(
                    ServiceItem("1", "Business Grant", "Economic Dev", "Financial support for startups."),
                    ServiceItem("2", "Food License", "Health Dept", "Permit for restaurants and cafes."),
                    ServiceItem("3", "Building Permit", "Housing", "Construction and renovation approval."),
                    ServiceItem("4", "Driver's License", "Transport", "Apply for or renew your license.")
                )
                _state.value = ServicesState.Success(dummyServices)
            }
        }
    }

    // ... rest of the file ...

    // Fetch Single Service Detail
    // FIXED: Renamed to 'loadServiceDetail' and removed 'userToken'
    fun loadServiceDetail(serviceId: String) {
        viewModelScope.launch {
            try {
                // Clear previous data first
                _selectedService.value = null

                // FIXED: Uses RetrofitClient and expects ServiceDetail response
                val response = RetrofitClient.apiService.getServiceDetail(serviceId)
                _selectedService.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}