package com.fintech.superadmin.data.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ProductDto(
	@field:SerializedName("STATUS")
	val sTATUS: String? = null,
	@field:SerializedName("IMAGE")
	val iMAGE: String? = null,
	@field:SerializedName("COURIER_CHARGE")
	val cOURIERCHARGE: String? = null,
	@field:SerializedName("PRICE")
	val pRICE: String? = null,
	@field:SerializedName("DESCRIPTION")
	val dESCRIPTION: String? = null,
	@field:SerializedName("CATEGORY")
	val cATEGORY: String? = null,
	@field:SerializedName("ID")
	val iD: String? = null,
	@field:SerializedName("SUBCATEGORY")
	val sUBCATEGORY: String? = null,
	@field:SerializedName("DATE_TIME")
	val dATETIME: String? = null,
	@field:SerializedName("NAME")
	val nAME: String? = null,
	@field:SerializedName("DISCOUNT")
	val dISCOUNT: String? = null
) : Parcelable
