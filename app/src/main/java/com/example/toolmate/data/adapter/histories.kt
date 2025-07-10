package com.example.toolmate.data.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class histories(
    val imgMedia: String,
    val nameMedia: String,
    val dateDownload: String
): Parcelable