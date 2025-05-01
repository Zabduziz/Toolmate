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

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("full_name")
	val fullName: String,

	@field:SerializedName("comments")
	val comments: Int,

	@field:SerializedName("caption")
	val caption: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("videos")
	val videos: List<String>,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("likes")
	val likes: Int
)
