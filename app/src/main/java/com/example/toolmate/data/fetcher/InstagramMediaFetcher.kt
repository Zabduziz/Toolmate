package com.example.toolmate.data.fetcher

import android.util.Log
import com.example.toolmate.data.response.InstagramResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstagramMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getInstagramInfo(url)
        client.enqueue(object : Callback<InstagramResponse> {
            override fun onResponse(call: Call<InstagramResponse?>, response: Response<InstagramResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    val linksDownload = mutableListOf<String>()
                    linksDownload.addAll(data?.images ?: emptyList())
                    linksDownload.addAll(data?.videos ?: emptyList())
                    val thumbnail = data?.images?.firstOrNull() ?: data?.videos?.firstOrNull()
                    val result = MediaResult(
                        thumbnail = thumbnail.toString(),
                        title = data?.caption,
                        links = linksDownload
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }
            override fun onFailure(call: Call<InstagramResponse?>, t: Throwable) {
                Log.e("InstagramMediaFetcher", "API call failed: ${t.localizedMessage}")
            }
        })
    }
}