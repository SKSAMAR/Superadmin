package com.fintech.superadmin.data.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrderDto(

	@field:SerializedName("MAIN_OWNER_ID")
	val mAINOWNERID: String? = null,

	@field:SerializedName("USER_ID")
	val uSERID: String? = null,

	@field:SerializedName("STATE")
	val sTATE: String? = null,

	@field:SerializedName("PRODUCT_INFO")
	val pRODUCTINFO: ProductDto? = null,

	@field:SerializedName("LAST_NAME")
	val lASTNAME: String? = null,

	@field:SerializedName("DELIVERY_TIME")
	val dELIVERYTIME: String? = null,

	@field:SerializedName("FIRST_NAME")
	val fIRSTNAME: String? = null,

	@field:SerializedName("PIN_CODE")
	val pINCODE: String? = null,

	@field:SerializedName("CLOSING_BALANCE")
	val cLOSINGBALANCE: String? = null,

	@field:SerializedName("STATUS")
	val sTATUS: String? = null,

	@field:SerializedName("COMM")
	val cOMM: String? = null,

	@field:SerializedName("AMOUNT")
	val aMOUNT: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("REMARK")
	val rEMARK: String? = null,

	@field:SerializedName("OPENING_BALANCE")
	val oPENINGBALANCE: String? = null,

	@field:SerializedName("TDS")
	val tDS: String? = null,

	@field:SerializedName("PAYMENT_MODE")
	val pAYMENTMODE: String? = null,

	@field:SerializedName("ORDER_DATE")
	val oRDERDATE: String? = null,

	@field:SerializedName("GST")
	val gST: String? = null,

	@field:SerializedName("PRODUCT_ID")
	val pRODUCTID: String? = null,

	@field:SerializedName("DATE")
	val dATE: String? = null,

	@field:SerializedName("LANDMARK")
	val lANDMARK: String? = null,

	@field:SerializedName("LAST_UPDATE")
	val lASTUPDATE: String? = null,

	@field:SerializedName("CITY")
	val cITY: String? = null,

	@field:SerializedName("DELIVERY_DATE")
	val dELIVERYDATE: String? = null,

	@field:SerializedName("ADDRESS")
	val aDDRESS: String? = null,

	@field:SerializedName("MAIN_OWNER")
	val mAINOWNER: String? = null,

	@field:SerializedName("ORDER_TIME")
	val oRDERTIME: String? = null,

	@field:SerializedName("TRANSACTION_ID")
	val tRANSACTIONID: String? = null
) : Parcelable
