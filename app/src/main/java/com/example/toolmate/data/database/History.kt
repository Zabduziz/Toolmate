package com.example.toolmate.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0,

    @ColumnInfo(name="userid")
    var userid: String? = null,

    @ColumnInfo(name="medianame")
    var medianame: String? = null,

    @ColumnInfo(name="datedownload")
    var datedownload: String? = null,

    @ColumnInfo(name="thumbnail")
    var thumbnails: String? = null,

    @ColumnInfo(name="downloadlink")
    var downloadlink: String? = null

) : Parcelable