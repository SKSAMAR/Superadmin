package com.fintech.superadmin.data.apiResponse.fingpay.boad


import com.google.gson.annotations.SerializedName

data class OnboardPanResponse(
    @SerializedName("data")
    var data: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("statusCode")
    var statusCode: Int
)