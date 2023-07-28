package com.fintech.superadmin.data.eko

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EkoSendOtpResponse(

	@field:SerializedName("response_status_id")
	val responseStatusId: Int? = null,

	@field:SerializedName("data")
	val data: OTPData? = null,

	@field:SerializedName("response_type_id")
	val responseTypeId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class OTPData(

	@field:SerializedName("customer_id_type")
	val customerIdType: String? = null,

	@field:SerializedName("user_code")
	val userCode: String? = null,

	@field:SerializedName("state_desc")
	val stateDesc: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("pipe")
	val pipe: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("list_specific_id")
	val listSpecificId: String? = null,

	@field:SerializedName("otp_ref_id")
	val otpRefId: String? = null
) : Parcelable
