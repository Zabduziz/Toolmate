package com.example.toolmate.data.fetcher

import com.example.toolmate.data.response.TiktokResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TiktokMediaFetcher: MediaFetcher {
    override fun fetchMedia(
        url: String,
        onResult: (MediaResult?) -> Unit
    ) {
        val client = ApiConfig.getApiService().getTiktokInfo(url)
        client.enqueue(object : Callback<TiktokResponse> {
            override fun onResponse(
                call: Call<TiktokResponse?>,
                response: Response<TiktokResponse?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    val result = MediaResult(
                        thumbnail = data?.media?.coverUrl,
                        title = data?.title,
                        duration = data?.duration.toString(),
                        links = listOf(data?.media?.videoUrl.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }
            override fun onFailure(
                call: Call<TiktokResponse?>,
                response: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}