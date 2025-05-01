package com.example.toolmate.data.fetcher

data class MediaResult(
    val thumbnail: String?,
    val title: String?,
    val duration: String? = null,
    val links: List<String>?
)