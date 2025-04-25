package com.example.toolmate.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Base URL for your API
    private const val BASE_URL = "https://fastrestapis.fasturl.cloud/"

    // Create Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Set the base URL
        .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter to handle JSON responses
        .build()

    // API Interface that defines your endpoints
    val api: ApiInterface = retrofit.create(ApiInterface::class.java)
}
