package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class SoundCloudResponse(

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("data")
	val data: SoundCloudData,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class SoundCloudData(

	@field:SerializedName("file")
	val file: String,

	@field:SerializedName("bitrate")
	val bitrate: String,

	@field:SerializedName("title")
	val title: String
)
