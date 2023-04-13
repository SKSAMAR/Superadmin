package com.fintech.paytcash.data_model.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RequestedHistoryModel(

	@field:SerializedName("DATE")
	val dATE: String? = null,

	@field:SerializedName("STATUS")
	val sTATUS: String? = null,

	@field:SerializedName("PAYMENT_MODE")
	val pAYMENTMODE: String? = null,

	@field:SerializedName("AMOUNT")
	val aMOUNT: String? = null,

	@field:SerializedName("FUND_TYPE")
	val fUNDTYPE: String? = null,

	@field:SerializedName("USER_ID")
	val uSERID: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("TRANSACTION_ID")
	val tRANSACTIONID: String? = null,

	@field:SerializedName("REMARK")
	val rEMARK: String? = null
) : Parcelable
