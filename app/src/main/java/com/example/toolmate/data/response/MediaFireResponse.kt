package com.example.toolmate.data.response

import com.google.gson.annotations.SerializedName

data class MediaFireResponse(

	@field:SerializedName("result")
	val result: MediaFireResult,

	@field:SerializedName("creator")
	val creator: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("status")
	val status: Int
)

data class Permissions(

	@field:SerializedName("explicit")
	val explicit: String,

	@field:SerializedName("read")
	val read: String,

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("write")
	val write: String
)

data class MediaFireResult(

	@field:SerializedName("owner")
	val owner: String,

	@field:SerializedName("filetype")
	val filetype: String,

	@field:SerializedName("quickkey")
	val quickkey: String,

	@field:SerializedName("flag")
	val flag: String,

	@field:SerializedName("cookie")
	val cookie: String,

	@field:SerializedName("edit")
	val edit: String,

	@field:SerializedName("created")
	val created: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("privacy")
	val privacy: String,

	@field:SerializedName("revision")
	val revision: String,

	@field:SerializedName("download")
	val download: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("view")
	val view: String,

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("size")
	val size: String,

	@field:SerializedName("ready")
	val ready: String,

	@field:SerializedName("permissions")
	val permissions: Permissions,

	@field:SerializedName("mimetype")
	val mimetype: String,

	@field:SerializedName("hash")
	val hash: String
)
