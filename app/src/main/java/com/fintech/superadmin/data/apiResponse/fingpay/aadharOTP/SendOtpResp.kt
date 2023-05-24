package com.fintech.superadmin.data.apiResponse.fingpay.aadharOTP


import com.google.gson.annotations.SerializedName

data class SendOtpResp(
    @SerializedName("data")
    var data: SendOTPResponse?,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("statusCode")
    var statusCode: Int?
){
    override fun toString(): String {
        return "SendOtpResp(data=$data, message='$message', status=$status, statusCode=$statusCode)"
    }
}