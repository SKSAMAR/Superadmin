package com.fintech.superadmin.data.apiResponse.fingpay.aadharOTP


import com.google.gson.annotations.SerializedName

data class SendOTPResponse(
    @SerializedName("encodeFPTxnId")
    var encodeFPTxnId: String?,
    @SerializedName("primaryKeyId")
    var primaryKeyId: Int?
)