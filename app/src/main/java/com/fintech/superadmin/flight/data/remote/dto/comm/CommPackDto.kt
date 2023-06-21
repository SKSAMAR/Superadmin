package com.fintech.superadmin.flight.data.remote.dto.comm

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CommPackDto(

	@field:SerializedName("TDS")
	val TDS: String? = null,

	@field:SerializedName("MAIN_OWNER_ID")
	val MAIN_OWNER_ID: String? = null,

	@field:SerializedName("MAX_AMOUNT")
	val MAX_AMOUNT: String? = null,

	@field:SerializedName("AMOUNT_TYPE")
	val AMOUNT_TYPE: String? = null,

	@field:SerializedName("GST")
	val GST: String? = null,

	@field:SerializedName("MS_COM")
	val MS_COM: String? = null,

	@field:SerializedName("OWNER_ID")
	val OWNER_ID: String? = null,

	@field:SerializedName("COMM_PACK_ID")
	val COMM_PACK_ID: String? = null,

	@field:SerializedName("DATE")
	val DATE: String? = null,

	@field:SerializedName("OWNER")
	val OWNER: String? = null,

	@field:SerializedName("STATUS")
	val STATUS: String? = null,

	@field:SerializedName("OPERATOR_ID")
	val OPERATOR_ID: String? = null,

	@field:SerializedName("OPERATOR")
	val OPERATOR: String? = null,

	@field:SerializedName("AMOUNT")
	val AMOUNT: String? = null,

	@field:SerializedName("MIN_AMOUNT")
	val MIN_AMOUNT: String? = null,

	@field:SerializedName("DS_COM")
	val DS_COM: String? = null,

	@field:SerializedName("PROVIDER")
	val PROVIDER: String? = null,

	@field:SerializedName("SERVICE")
	val SERVICE: String? = null,

	@field:SerializedName("ID")
	val ID: String? = null,

	@field:SerializedName("MAIN_OWNER")
	val MAIN_OWNER: String? = null,

	@field:SerializedName("TYPE")
	val TYPE: String? = null,

	@field:SerializedName("CHARGE")
	val CHARGE: String? = null
) : Parcelable
