package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class TeraboxResponse(

	@field:SerializedName("result")
	val result: List<TeraboxResultItem>,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class TeraboxResultItem(

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("size")
	val size: String,

	@field:SerializedName("create_time")
	val createTime: String,

	@field:SerializedName("is_dir")
	val isDir: String,

	@field:SerializedName("downloadUrl")
	val downloadUrl: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("fs_id")
	val fsId: String
)
