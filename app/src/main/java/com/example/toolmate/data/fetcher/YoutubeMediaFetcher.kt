package com.example.toolmate.data.fetcher

import android.util.Log
import com.example.toolmate.data.response.YoutubeResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getYoutubeVideo(url)
        client.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(call: Call<YoutubeResponse?>, response: Response<YoutubeResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    val result = MediaResult(
                        thumbnail = data?.metadata?.thumbnail,
                        title = data?.title,
                        duration = data?.metadata?.duration,
                        links = listOf(data?.media.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }
            override fun onFailure(call: Call<YoutubeResponse?>, t: Throwable) {
                Log.e("YoutubeMediaFetcher", "API call failed: ${t.localizedMessage}")
            }
        })
    }
}