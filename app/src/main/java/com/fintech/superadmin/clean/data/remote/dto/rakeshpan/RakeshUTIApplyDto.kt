package com.fintech.superadmin.clean.data.remote.dto.rakeshpan

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RakeshUTIApplyDto(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("vle_id")
	val vleId: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("txn_status")
	val txnStatus: String? = null,

	@field:SerializedName("vle_status")
	val vleStatus: String? = null
) : Parcelable
