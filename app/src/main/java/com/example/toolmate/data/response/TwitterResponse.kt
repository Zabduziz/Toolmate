package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class TwitterResponse(

	@field:SerializedName("result")
	val result: TwitterResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class TwitterResult(

	@field:SerializedName("videosd")
	val videosd: String,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("videohd")
	val videohd: String,

	@field:SerializedName("audio")
	val audio: String,

	@field:SerializedName("desc")
	val desc: String
)
