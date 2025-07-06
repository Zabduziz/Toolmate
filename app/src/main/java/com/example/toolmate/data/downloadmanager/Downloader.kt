package com.example.toolmate.data.downloadmanager

interface Downloader {
    fun downloadFile(url: String): Long
}