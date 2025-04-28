package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class InstagramResponse(

	@field:SerializedName("result")
	val result: InstagramResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class InstagramResult(

	@field:SerializedName("data")
	val data: List<InstagramDataItem>,

	@field:SerializedName("status")
	val status: Boolean
)

data class InstagramDataItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("url")
	val url: String
)
