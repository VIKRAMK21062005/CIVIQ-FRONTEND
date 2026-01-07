package com.example.civiq.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civiq.model.LoginRequest
import com.example.civiq.model.RegisterRequest
import com.example.civiq.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> = _loginState

    fun loginUser(email: String, pass: String) {
        viewModelScope.launch {
            try {
                // 1. Create the Request Object
                val request = LoginRequest(email = email, password = pass)

                // 2. Call the API (Do NOT put @POST here)
                val response = RetrofitInstance.api.loginUser(request)

                // 3. Handle Result
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.access_token
                    _loginState.value = Result.success(token)
                    Log.d("Auth", "Login Success: $token")
                } else {
                    _loginState.value = Result.failure(Exception("Login Failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
                Log.e("Auth", "Error: ${e.message}")
            }
        }
    }

    // Add registerUser function here if needed, following the same pattern
}