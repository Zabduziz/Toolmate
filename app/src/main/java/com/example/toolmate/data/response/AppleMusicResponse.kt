package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class AppleMusicResponse(

	@field:SerializedName("result")
	val result: AppleMusicResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class AppleMusicResult(

	@field:SerializedName("metadata")
	val metadata: AppleMusicMetadata,

	@field:SerializedName("downloadUrl")
	val downloadUrl: String
)

data class AppleMusicMetadata(

	@field:SerializedName("keywords")
	val keywords: String,

	@field:SerializedName("appleDescription")
	val appleDescription: String,

	@field:SerializedName("pageTitle")
	val pageTitle: String,

	@field:SerializedName("artworkUrl")
	val artworkUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("appleTitle")
	val appleTitle: String,

	@field:SerializedName("musicReleaseDate")
	val musicReleaseDate: String
)
