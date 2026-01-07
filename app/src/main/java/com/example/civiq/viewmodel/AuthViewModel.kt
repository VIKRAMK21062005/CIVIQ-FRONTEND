package com.example.civiq.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.civiq.model.LoginRequest
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

                // 2. Call the API
                val response = RetrofitInstance.api.loginUser(request)

                // 3. Handle Result
                if (response.isSuccessful && response.body() != null) {
                    // FIX: Handle nullable access_token
                    val token = response.body()?.access_token

                    if (!token.isNullOrEmpty()) {
                        // Token exists, so we can safely cast to non-nullable String
                        _loginState.value = Result.success(token)
                        Log.d("Auth", "Login Success: $token")
                    } else {
                        // API succeeded but token was null/empty
                        _loginState.value = Result.failure(Exception("Login Failed: Token is missing"))
                    }
                } else {
                    _loginState.value = Result.failure(Exception("Login Failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
                Log.e("Auth", "Error: ${e.message}")
            }
        }
    }
}