package com.example.toolmate.data.fetcher

import android.util.Log
import com.example.toolmate.data.response.SnackVideoResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SnackVideoMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getSnackVideoInfo(url)
        client.enqueue(object : Callback<SnackVideoResponse> {
            override fun onResponse(call: Call<SnackVideoResponse?>, response: Response<SnackVideoResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result
                    val result = MediaResult(
                        thumbnail = data?.thumbnail,
                        title = data?.title,
                        duration = null,
                        links = listOf(data?.media.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<SnackVideoResponse?>, t: Throwable) {
                Log.e("SnackVideoMediaFetcher", "API call failed: ${t.localizedMessage}")
            }
        })
    }
}