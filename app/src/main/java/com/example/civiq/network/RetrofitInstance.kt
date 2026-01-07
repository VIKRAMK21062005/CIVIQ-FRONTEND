package com.example.civiq.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // USE THIS FOR EMULATOR:
    //private const val BASE_URL = "http://10.0.2.2:8000/"

    // USE THIS FOR REAL PHONE (Replace with your Laptop's Current IP):
     private const val BASE_URL = "http://192.168.43.50:8000/"

    val api: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
}