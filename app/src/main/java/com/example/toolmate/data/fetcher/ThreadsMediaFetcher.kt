package com.example.toolmate.data.fetcher

import com.example.toolmate.data.response.ThreadsResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThreadsMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getThreadsInfo(url)
        client.enqueue(object : Callback<ThreadsResponse> {
            override fun onResponse(call: Call<ThreadsResponse?>, response: Response<ThreadsResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val result = MediaResult(
                        thumbnail = data?.result?.url.toString(),
                        title = data?.result?.caption,
                        duration = null,
                        links = listOf(data?.result?.url.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<ThreadsResponse?>, response: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}