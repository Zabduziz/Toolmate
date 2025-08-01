package com.example.toolmate.data.fetcher

import android.util.Log
import com.example.toolmate.data.response.TwitterResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TwitterMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getTwitterInfo(url)
        client.enqueue(object : Callback<TwitterResponse> {
            override fun onResponse(call: Call<TwitterResponse?>, response: Response<TwitterResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    val result = MediaResult(
                        thumbnail = data?.thumb,
                        title = data?.desc,
                        duration = null,
                        links = listOf(data?.videohd.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<TwitterResponse?>, t: Throwable) {
                Log.e("TwitterMediaFetcher", "API call failed: ${t.localizedMessage}")
            }

        })
    }
}