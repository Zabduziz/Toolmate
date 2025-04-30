package com.example.toolmate.data.fetcher

import com.example.toolmate.data.response.YoutubeResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeMediaFetcher: MediaFetcher {
    override fun fetchMedia(
        url: String,
        onResult: (MediaResult?) -> Unit
    ) {
        val client = ApiConfig.getApiService().getYoutubeVideo(url)
        client.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(
                call: Call<YoutubeResponse?>,
                response: Response<YoutubeResponse?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    val result = MediaResult(
                        thumbnail = data?.metadata?.thumbnail,
                        title = data?.title,
                        duration = data?.metadata?.duration
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }
            override fun onFailure(
                call: Call<YoutubeResponse?>,
                response: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}