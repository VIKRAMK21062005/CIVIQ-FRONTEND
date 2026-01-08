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

    // --- Login State ---
    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> = _loginState

    // --- Register State (THIS WAS MISSING) ---
    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> = _registerState

    // --- Login Function ---
    fun loginUser(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email = email, password = pass)
                val response = RetrofitInstance.api.loginUser(request)

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.access_token
                    if (!token.isNullOrEmpty()) {
                        _loginState.value = Result.success(token)
                    } else {
                        _loginState.value = Result.failure(Exception("Login Failed: Token is missing"))
                    }
                } else {
                    _loginState.value = Result.failure(Exception("Login Failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
                Log.e("Auth", "Login Error", e)
            }
        }
    }

    // --- Register Function (THIS WAS MISSING) ---
    fun registerUser(name: String, email: String, pass: String) {
        viewModelScope.launch {
            try {
                // 1. Create Request
                val request = RegisterRequest(email = email, password = pass, full_name = name)

                // 2. Call API
                val response = RetrofitInstance.api.registerUser(request)

                // 3. Handle Response
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()?.access_token
                    if (!token.isNullOrEmpty()) {
                        _registerState.value = Result.success(token)
                    } else {
                        _registerState.value = Result.failure(Exception("Registration successful but token missing"))
                    }
                } else {
                    _registerState.value = Result.failure(Exception("Registration Failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                _registerState.value = Result.failure(e)
                Log.e("Auth", "Register Error", e)
            }
        }
    }
}