package com.fintech.superadmin.data.apiResponse.fingpay


import com.google.gson.annotations.SerializedName

data class FingpayFetchBank(
    @SerializedName("data")
    var data: List<FingPayBank>,
    @SerializedName("message")
    var message: String,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("statusCode")
    var statusCode: Int
)