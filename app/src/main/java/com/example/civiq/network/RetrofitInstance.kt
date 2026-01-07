package com.example.civiq.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // UPDATED: Using your specific local IP address
    private const val BASE_URL = "http://192.168.43.50:8000/"

    val api: CiviqApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CiviqApiService::class.java)
    }
}