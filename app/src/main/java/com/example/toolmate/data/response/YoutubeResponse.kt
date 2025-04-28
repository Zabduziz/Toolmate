package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class YoutubeResponse(

	@field:SerializedName("result")
	val result: YoutubeResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class YoutubeMetadata(

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("uploadDate")
	val uploadDate: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lengthSeconds")
	val lengthSeconds: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("views")
	val views: String
)

data class YoutubeAuthor(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("url")
	val url: String
)

data class YoutubeResult(

	@field:SerializedName("metadata")
	val metadata: YoutubeMetadata,

	@field:SerializedName("author")
	val author: YoutubeAuthor,

	@field:SerializedName("media")
	val media: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("quality")
	val quality: String
)
