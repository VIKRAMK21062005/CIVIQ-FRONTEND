package com.example.civiq.network

import com.example.civiq.model.LoginRequest
import com.example.civiq.model.LoginResponse
import com.example.civiq.model.RegisterRequest
import com.example.civiq.model.ServiceItem
// IMPORTANT: These imports were missing
import com.example.civiq.model.ApplicationRequest
import com.example.civiq.model.ApplicationResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CiviqApiService {

    // Auth endpoints
    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<LoginResponse>

    // Service endpoints
    @GET("services")
    suspend fun getServices(
        @Header("Authorization") token: String
    ): List<ServiceItem>

    @GET("services/{id}")
    suspend fun getServiceDetail(
        @Header("Authorization") token: String,
        @Path("id") serviceId: String
    ): ServiceItem

    // Application endpoint (Now valid with imports)
    @POST("applications")
    suspend fun submitApplication(
        @Header("Authorization") token: String,
        @Body request: ApplicationRequest
    ): Response<ApplicationResponse>
}