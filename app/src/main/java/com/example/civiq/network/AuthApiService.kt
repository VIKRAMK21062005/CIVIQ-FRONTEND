package com.example.civiq.network

import com.example.civiq.model.LoginRequest
import com.example.civiq.model.LoginResponse // Ensure this matches your model file
import com.example.civiq.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse> // CHANGED from TokenResponse to LoginResponse

    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<LoginResponse> // CHANGED from TokenResponse to LoginResponse
}