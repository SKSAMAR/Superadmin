package com.fintech.superadmin.data.eko

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EkoDtmTransactionHistory(

	@field:SerializedName("response_status_id")
	val responseStatusId: Int? = null,

	@field:SerializedName("data")
	val data: EkoTransactionData? = null,

	@field:SerializedName("response_type_id")
	val responseTypeId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class EkoTransactionData(

	@field:SerializedName("tx_status")
	val txStatus: String? = null,

	@field:SerializedName("debit_user_id")
	val debitUserId: String? = null,

	@field:SerializedName("tds")
	val tds: String? = null,

	@field:SerializedName("txstatus_desc")
	val txstatusDesc: String? = null,

	@field:SerializedName("fee")
	val fee: String? = null,

	@field:SerializedName("total_sent")
	val totalSent: String? = null,

	@field:SerializedName("channel")
	val channel: String? = null,

	@field:SerializedName("collectable_amount")
	val collectableAmount: String? = null,

	@field:SerializedName("txn_wallet")
	val txnWallet: String? = null,

	@field:SerializedName("utility_acc_no")
	val utilityAccNo: String? = null,

	@field:SerializedName("sender_name")
	val senderName: String? = null,

	@field:SerializedName("ekyc_enabled")
	val ekycEnabled: String? = null,

	@field:SerializedName("remaining_limit_before_pan_required")
	val remainingLimitBeforePanRequired: Double? = null,

	@field:SerializedName("tid")
	val tid: String? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("insurance_acquired")
	val insuranceAcquired: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("user_code")
	val userCode: String? = null,

	@field:SerializedName("totalfee")
	val totalfee: String? = null,

	@field:SerializedName("next_allowed_limit")
	val nextAllowedLimit: String? = null,

	@field:SerializedName("is_otp_required")
	val isOtpRequired: String? = null,

	@field:SerializedName("aadhar")
	val aadhar: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("commission")
	val commission: String? = null,

	@field:SerializedName("pipe")
	val pipe: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("bank_ref_num")
	val bankRefNum: String? = null,

	@field:SerializedName("recipient_id")
	val recipientId: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null,

	@field:SerializedName("gst_loss")
	val gstLoss: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("pan_required")
	val panRequired: Int? = null,

	@field:SerializedName("pinNo")
	val pinNo: String? = null,

	@field:SerializedName("gst_benefit")
	val gstBenefit: String? = null,

	@field:SerializedName("payment_mode_desc")
	val paymentModeDesc: String? = null,

	@field:SerializedName("channel_desc")
	val channelDesc: String? = null,

	@field:SerializedName("last_used_okekey")
	val lastUsedOkekey: String? = null,

	@field:SerializedName("client_ref_id")
	val clientRefId: String? = null,

	@field:SerializedName("npr")
	val npr: String? = null,

	@field:SerializedName("insurance_amount")
	val insuranceAmount: String? = null,

	@field:SerializedName("service_tax")
	val serviceTax: String? = null,

	@field:SerializedName("paymentid")
	val paymentid: String? = null,

	@field:SerializedName("mdr")
	val mdr: String? = null,

	@field:SerializedName("recipient_name")
	val recipientName: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("account")
	val account: String? = null,

	@field:SerializedName("kyc_state")
	val kycState: String? = null
) : Parcelable
