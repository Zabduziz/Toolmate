package com.example.toolmate.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("downup/igdown")
    fun downloadInstagram(@Query("url") url: String): Call<FileResponse>

    @GET("downup/ytdown")
    fun downloadYoutube(@Query("url") url: String): Call<FileResponse>
}
