package com.fintech.superadmin.data.dto

import com.google.gson.annotations.SerializedName

data class AtmProceedableDto(

	@field:SerializedName("reference")
	val reference: String? = null,
	@field:SerializedName("response_code")
	val responseCode: Int? = null,
	@field:SerializedName("atmType")
	val atmType: String? = null,
	@field:SerializedName("message")
	val message: String? = null,
	@field:SerializedName("status")
	val status: Boolean? = null
)
