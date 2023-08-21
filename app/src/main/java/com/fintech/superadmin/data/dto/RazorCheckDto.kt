package com.fintech.superadmin.data.dto

import com.google.gson.annotations.SerializedName

//@Parcelize
data class RazorCheckDto(

	@field:SerializedName("toBoard")
	val toBoard: Boolean? = null,

	@field:SerializedName("virtual_account_bank")
	val virtualAccountBank: Any? = null
) //: Parcelable
