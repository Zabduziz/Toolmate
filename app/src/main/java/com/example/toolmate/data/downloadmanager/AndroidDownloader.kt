package com.example.toolmate.data.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(private val context: Context, nameFile: String): Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    private var nameFile: String? = nameFile

    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(getMimeType(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(nameFile)
            .addRequestHeader("Authorized", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameFile)
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