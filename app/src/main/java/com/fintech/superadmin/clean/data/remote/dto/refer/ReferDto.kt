package com.fintech.superadmin.clean.data.remote.dto.refer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReferDto(

	@field:SerializedName("DATE")
	val dATE: String? = null,

	@field:SerializedName("REFER_AMOUNT")
	val rEFERAMOUNT: String? = null,

	@field:SerializedName("COMMISSION_PERCENT")
	val cOMMISSIONPERCENT: String? = null,

	@field:SerializedName("TOTAL_EARNED")
	val tOTALEARNED: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("REFER_TEXT")
	val rEFERTEXT: String? = null,

	@field:SerializedName("COMMISSION_TEXT")
	val cOMMISSIONTEXT: String? = null,

	@field:SerializedName("REFER_CODE")
	val rEFERCODE: String? = null,

	@field:SerializedName("REFER_URL")
	val REFER_URL: String? = null,
) : Parcelable
