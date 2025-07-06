package com.example.toolmate.data.fetcher

import com.example.toolmate.data.response.FacebookResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FacebookMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getFacebookInfo(url)
        client.enqueue(object : Callback<FacebookResponse> {
            override fun onResponse(call: Call<FacebookResponse?>, response: Response<FacebookResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val result = MediaResult(
                        thumbnail = data?.result?.thumbnail,
                        title = data?.result?.title,
                        duration = data?.result?.durationMs.toString(),
                        links = listOf(data?.result?.sd.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(p0: Call<FacebookResponse?>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}