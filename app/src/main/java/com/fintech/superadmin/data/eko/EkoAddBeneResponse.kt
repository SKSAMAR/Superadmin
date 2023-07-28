package com.fintech.superadmin.data.eko

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EkoAddBeneResponse(

	@field:SerializedName("response_status_id")
	val responseStatusId: Int? = null,

	@field:SerializedName("data")
	val data: AddData? = null,

	@field:SerializedName("response_type_id")
	val responseTypeId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class AddData(

	@field:SerializedName("user_code")
	val userCode: String? = null,

	@field:SerializedName("initiator_id")
	val initiatorId: String? = null,

	@field:SerializedName("recipient_mobile")
	val recipientMobile: String? = null,

	@field:SerializedName("recipient_id_type")
	val recipientIdType: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("pipes")
	val pipes: Pipes? = null,

	@field:SerializedName("recipient_id")
	val recipientId: Int? = null
) : Parcelable
