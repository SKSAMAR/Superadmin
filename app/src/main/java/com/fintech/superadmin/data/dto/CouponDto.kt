package com.fintech.superadmin.data.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CouponDto(

	@field:SerializedName("DATE")
	val dATE: String? = null,

	@field:SerializedName("STATUS")
	val sTATUS: String? = null,

	@field:SerializedName("NUM")
	val nUM: String? = null,

	@field:SerializedName("PSA_ID")
	val pSAID: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("OD_ID")
	val oDID: String? = null,

	@field:SerializedName("TYPE")
	val tYPE: String? = null
) : Parcelable
