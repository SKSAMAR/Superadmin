package com.fintech.kkpayments.data.qrPayement


import com.google.gson.annotations.SerializedName

data class RazorPayUPIResponse(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("batch_id")
    val batchId: Any,
    @SerializedName("created_at")
    val createdAt: Int,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("entity")
    val entity: String,
    @SerializedName("error")
    val error: Error,
    @SerializedName("failure_reason")
    val failureReason: Any,
    @SerializedName("fee_type")
    val feeType: String,
    @SerializedName("fees")
    val fees: Int,
    @SerializedName("fund_account_id")
    val fundAccountId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("narration")
    val narration: String,
    @SerializedName("notes")
    val notes: Notes,
    @SerializedName("purpose")
    val purpose: String,
    @SerializedName("reference_id")
    val referenceId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("status_details")
    val statusDetails: StatusDetails,
    @SerializedName("status_details_id")
    val statusDetailsId: Any,
    @SerializedName("tax")
    val tax: Int,
    @SerializedName("utr")
    val utr: Any
)