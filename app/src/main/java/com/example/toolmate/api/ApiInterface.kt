package com.example.toolmate.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // YouTube Video Download - With URL and optional Quality parameter
    @GET("downup/ytmp4")
    fun downloadYoutubeVideo(
        @Query("url") url: String,
        @Query("quality") quality: String = "720" // Default quality is 720
    ): Call<FileResponse>

    // Instagram Video Download
    @GET("downup/igdown")
    fun downloadInstagramVideo(@Query("url") url: String): Call<FileResponse>

    // TikTok Video Download
    @GET("downup/tiktokdown")
    fun downloadTiktokVideo(@Query("url") url: String): Call<FileResponse>

    // Twitter Video Download
    @GET("downup/twdown")
    fun downloadTwitterVideo(@Query("url") url: String): Call<FileResponse>

    // Facebook Video Download
    @GET("downup/fbdown")
    fun downloadFacebookVideo(@Query("url") url: String): Call<FileResponse>

    // Snack Video Download
    @GET("downup/snackvideodown")
    fun downloadSnackVideo(@Query("url") url: String): Call<FileResponse>

    // Spotify Track Download
    @GET("downup/spotifydown")
    fun downloadSpotifyTrack(@Query("url") url: String): Call<FileResponse>

    // SoundCloud Track Download
    @GET("downup/soundclouddown")
    fun downloadSoundcloudTrack(@Query("url") url: String): Call<FileResponse>

    // Apple Music Track Download
    @GET("downup/applemusicdown")
    fun downloadAppleMusicTrack(@Query("url") url: String): Call<FileResponse>

    // Terabox File Download
    @GET("downup/teraboxdown")
    fun downloadTeraboxFile(@Query("url") url: String): Call<FileResponse>

    // MediaFire File Download
    @GET("downup/mediafiredown")
    fun downloadMediaFireFile(@Query("url") url: String): Call<FileResponse>

    // Bstation File Download
    @GET("downup/bstationdown")
    fun downloadBstationFile(@Query("url") url: String): Call<FileResponse>
}
