package com.example.toolmate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class apps(
    val img: String,
    val name: String
): Parcelable
