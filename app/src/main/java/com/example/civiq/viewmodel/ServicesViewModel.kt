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

    private val _state = MutableStateFlow<ServicesState>(ServicesState.Loading)
    val state: StateFlow<ServicesState> = _state

    fun fetchServices(userToken: String) {
        viewModelScope.launch {
            _state.value = ServicesState.Loading
            try {
                // formattedToken adds "Bearer " if your backend expects it
                val formattedToken = if (userToken.startsWith("Bearer ")) userToken else "Bearer $userToken"

                val response = RetrofitInstance.api.getServices(formattedToken)
                _state.value = ServicesState.Success(response)
            } catch (e: Exception) {
                _state.value = ServicesState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}