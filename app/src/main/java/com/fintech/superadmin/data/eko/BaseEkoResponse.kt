package com.fintech.superadmin.data.eko

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class BaseEkoResponse(

	@field:SerializedName("response_status_id")
	val responseStatusId: Int? = null,

	@field:SerializedName("response_type_id")
	val responseTypeId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable
