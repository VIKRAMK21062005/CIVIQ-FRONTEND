package com.example.civiq.network

import com.example.civiq.model.LoginRequest
import com.example.civiq.model.LoginResponse
import com.example.civiq.model.RegisterRequest
import com.example.civiq.model.ServiceItem
import retrofit2.Response // Required for .isSuccessful
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CiviqApiService {

    // Auth endpoints now return Response<T> to allow status checks
    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<LoginResponse>

    // Service endpoints can return the data directly (ViewModel handles try/catch)
    @GET("services")
    suspend fun getServices(
        @Header("Authorization") token: String
    ): List<ServiceItem>

    @GET("services/{id}")
    suspend fun getServiceDetail(
        @Header("Authorization") token: String,
        @Path("id") serviceId: String
    ): ServiceItem
}