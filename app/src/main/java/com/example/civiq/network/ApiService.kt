package com.example.civiq.network

import com.example.civiq.model.ApplicationRequest
import com.example.civiq.model.ApplicationResponse
import com.example.civiq.model.ServiceDetail
import com.example.civiq.model.ServiceItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("services")
    suspend fun getServices(): List<ServiceItem>

    @GET("services/{id}")
    suspend fun getServiceDetail(@Path("id") id: String): ServiceDetail

    @POST("applications")
    suspend fun submitApplication(@Body application: ApplicationRequest): Response<ApplicationResponse>
}