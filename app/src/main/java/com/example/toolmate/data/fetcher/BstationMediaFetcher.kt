package com.example.toolmate.data.fetcher

import android.util.Log
import com.example.toolmate.data.response.BstationResponse
import com.example.toolmate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BstationMediaFetcher: MediaFetcher {
    override fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit) {
        val client = ApiConfig.getApiService().getBstationInfo(url)
        client.enqueue(object : Callback<BstationResponse> {
            override fun onResponse(call: Call<BstationResponse?>, response: Response<BstationResponse?>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val result = MediaResult(
                        thumbnail = data?.result?.metadata?.thumbnail,
                        title = data?.result?.metadata?.title,
                        duration = null,
                        links = listOf(data?.result?.download?.url.toString())
                    )
                    onResult(result)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<BstationResponse?>, t: Throwable) {
                Log.e("BstatitonMediaFetcher", "API call failed: ${t.localizedMessage}")
            }
        })
    }
}