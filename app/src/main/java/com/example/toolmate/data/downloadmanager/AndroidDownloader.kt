package com.example.toolmate.data.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(private val context: Context): Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String): Long {
        val filename = url.toUri().lastPathSegment ?: "file_downloaded"
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(getMimeType(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(filename)
            .addRequestHeader("Authorized", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        return downloadManager.enqueue(request)
    }

    private fun getMimeType(url: String): String {
        return when {
            url.endsWith(".jpg") || url.endsWith(".jpeg") -> "image/jpeg"
            url.endsWith(".png") -> "image/png"
            url.endsWith(".mp4") -> "video/mp4"
            url.endsWith(".mp3") -> "audio/mpeg"
            else -> "*/*"
        }
    }
}