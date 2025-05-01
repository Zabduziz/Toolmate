package com.example.toolmate.data.retrofit

import com.example.toolmate.data.response.AppleMusicResponse
import com.example.toolmate.data.response.BstationResponse
import com.example.toolmate.data.response.FacebookResponse
import com.example.toolmate.data.response.InstagramResponse
import com.example.toolmate.data.response.MediaFireResponse
import com.example.toolmate.data.response.SnackVideoResponse
import com.example.toolmate.data.response.SoundCloudResponse
import com.example.toolmate.data.response.SpotifyResponse
import com.example.toolmate.data.response.TeraboxResponse
import com.example.toolmate.data.response.ThreadsResponse
import com.example.toolmate.data.response.TiktokResponse
import com.example.toolmate.data.response.TwitterResponse
import com.example.toolmate.data.response.YoutubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // YouTube Video Download - With URL and optional Quality parameter
    @GET("downup/ytmp4")
    fun getYoutubeVideo(
        @Query("url") url: String,
    ): Call<YoutubeResponse>

    // Instagram Video Download
    @GET("downup/igdown/advanced")
    fun getInstagramInfo(@Query("url") url: String): Call<InstagramResponse>

    // TikTok Video Download
    @GET("downup/ttdown")
    fun getTiktokInfo(@Query("url") url: String): Call<TiktokResponse>

    // Twitter Video Download
    @GET("downup/twdown")
    fun getTwitterInfo(@Query("url") url: String): Call<TwitterResponse>

    // Facebook Video Download
    @GET("downup/fbdown")
    fun getFacebookInfo(@Query("url") url: String): Call<FacebookResponse>

    // Threads Video Download
    @GET("downup/threadsdown")
    fun getThreadsInfo(@Query("url") url: String): Call<ThreadsResponse>

    // Snack Video Download
    @GET("downup/snackvideodown")
    fun getSnackVideoInfo(@Query("url") url: String): Call<SnackVideoResponse>

    // Spotify Track Download
    @GET("downup/spotifydown")
    fun getSpotifyInfo(@Query("url") url: String): Call<SpotifyResponse>

    // SoundCloud Track Download
    @GET("downup/soundclouddown")
    fun getSoundCloudInfo(@Query("url") url: String): Call<SoundCloudResponse>

    // Apple Music Track Download
    @GET("downup/applemusicdown")
    fun getAppleMusicInfo(@Query("url") url: String): Call<AppleMusicResponse>

    // Terabox File Download
    @GET("downup/teraboxdown")
    fun getTeraboxInfo(@Query("url") url: String): Call<TeraboxResponse>

    // MediaFire File Download
    @GET("downup/mediafiredown")
    fun getMediafireInfo(@Query("url") url: String): Call<MediaFireResponse>

    // Bstation File Download
    @GET("downup/bstationdown")
    fun getBstationInfo(@Query("url") url: String): Call<BstationResponse>
}
