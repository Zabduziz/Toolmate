package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class FacebookResponse(

	@field:SerializedName("result")
	val result: FacebookResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class FacebookResult(

	@field:SerializedName("duration_ms")
	val durationMs: Int,

	@field:SerializedName("sd")
	val sd: String,

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("hd")
	val hd: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String
)
