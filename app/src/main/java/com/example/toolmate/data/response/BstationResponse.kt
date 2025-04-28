package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class BstationResponse(

	@field:SerializedName("result")
	val result: BstationResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class BstationMetadata(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("view")
	val view: String,

	@field:SerializedName("locate")
	val locate: String,

	@field:SerializedName("like")
	val like: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String
)

data class BstationDownload(

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("url")
	val url: String
)

data class BstationResult(

	@field:SerializedName("metadata")
	val metadata: BstationMetadata,

	@field:SerializedName("download")
	val download: BstationDownload
)
