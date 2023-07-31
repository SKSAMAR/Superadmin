package com.fintech.superadmin.data.network.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class BusBookRedirect(

	@field:SerializedName("response_code")
	val responseCode: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("encdata")
	val encdata: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
