package com.fintech.superadmin.data.eko

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EkoDmtFerchBeneficaryResponse(

	@field:SerializedName("response_status_id")
	val responseStatusId: Int? = null,

	@field:SerializedName("data")
	val data: BeneData? = null,

	@field:SerializedName("response_type_id")
	val responseTypeId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Pipes(

	@field:SerializedName("3")
	val jsonMember3: JsonMember3? = null
) : Parcelable

@Parcelize
data class BeneData(

	@field:SerializedName("pan_required")
	val panRequired: Int? = null,

	@field:SerializedName("user_code")
	val userCode: String? = null,

	@field:SerializedName("recipient_list")
	val recipientList: List<RecipientListItem?>? = null,

	@field:SerializedName("remaining_limit_before_pan_required")
	val remainingLimitBeforePanRequired: Double? = null,

	@field:SerializedName("is_insured")
	val isInsured: String? = null
) : Parcelable

@Parcelize
data class JsonMember3(

	@field:SerializedName("pipe")
	val pipe: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class RecipientListItem(

	@field:SerializedName("channel_absolute")
	val channelAbsolute: Int? = null,

	@field:SerializedName("available_channel")
	val availableChannel: Int? = null,

	@field:SerializedName("account_type")
	val accountType: String? = null,

	@field:SerializedName("ifsc_status")
	val ifscStatus: Int? = null,

	@field:SerializedName("is_self_account")
	val isSelfAccount: String? = null,

	@field:SerializedName("channel")
	val channel: Int? = null,

	@field:SerializedName("is_imps_scheduled")
	val isImpsScheduled: Int? = null,

	@field:SerializedName("recipient_id_type")
	val recipientIdType: String? = null,

	@field:SerializedName("imps_inactive_reason")
	val impsInactiveReason: String? = null,

	@field:SerializedName("allowed_channel")
	val allowedChannel: Int? = null,

	@field:SerializedName("is_verified")
	val isVerified: Int? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("is_otp_required")
	val isOtpRequired: String? = null,

	@field:SerializedName("recipient_mobile")
	val recipientMobile: String? = null,

	@field:SerializedName("recipient_name")
	val recipientName: String? = null,

	@field:SerializedName("ifsc")
	val ifsc: String? = null,

	@field:SerializedName("account")
	val account: String? = null,

	@field:SerializedName("pipes")
	val pipes: Pipes? = null,

	@field:SerializedName("recipient_id")
	val recipientId: Long? = null,

	@field:SerializedName("is_rblbc_recipient")
	val isRblbcRecipient: Int? = null
) : Parcelable
