package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class ThreadsResponse(

	@field:SerializedName("result")
	val result: ThreadsResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class ThreadsResult(

	@field:SerializedName("caption")
	val caption: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("username")
	val username: String
)
