package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class SnackVideoResponse(

	@field:SerializedName("result")
	val result: SnackVideoResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class SnackVideoResult(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("like")
	val like: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("authorImage")
	val authorImage: String,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("share")
	val share: String,

	@field:SerializedName("media")
	val media: String,

	@field:SerializedName("title")
	val title: String
)
