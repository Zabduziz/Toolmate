package com.example.toolmate.data.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class apps(
    val img: String,
    val name: String
): Parcelable
