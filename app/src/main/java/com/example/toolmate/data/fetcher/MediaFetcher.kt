package com.example.toolmate.data.fetcher

interface MediaFetcher {
    fun fetchMedia(url: String, onResult: (MediaResult?) -> Unit)
}