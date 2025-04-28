package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class SpotifyResponse(

	@field:SerializedName("result")
	val result: SpotifyResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class SpotifyResult(

	@field:SerializedName("server")
	val server: String,

	@field:SerializedName("metadata")
	val metadata: SpotofyMetadata,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("link")
	val link: String
)

data class SpotofyMetadata(

	@field:SerializedName("cover")
	val cover: String,

	@field:SerializedName("artists")
	val artists: String,

	@field:SerializedName("album")
	val album: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String
)
