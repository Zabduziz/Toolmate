package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class TiktokResponse(

	@field:SerializedName("result")
	val result: TiktokResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class TiktokMedia(

	@field:SerializedName("coverUrl")
	val coverUrl: String,

	@field:SerializedName("videoUrl")
	val videoUrl: String,

	@field:SerializedName("musicUrl")
	val musicUrl: String
)

data class TiktokResult(

	@field:SerializedName("duration")
	val duration: Int,

	@field:SerializedName("shares")
	val shares: Int,

	@field:SerializedName("playCount")
	val playCount: Int,

	@field:SerializedName("comments")
	val comments: Int,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("originalSound")
	val originalSound: TiktokOriginalSound,

	@field:SerializedName("media")
	val media: TiktokMedia,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("likes")
	val likes: Int
)

data class TiktokOriginalSound(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("title")
	val title: String
)
