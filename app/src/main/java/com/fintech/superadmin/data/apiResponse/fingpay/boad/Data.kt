package com.fintech.superadmin.data.apiResponse.fingpay.boad


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ipAddress")
    var ipAddress: Any?,
    @SerializedName("latitude")
    var latitude: Double?,
    @SerializedName("longitude")
    var longitude: Double?,
    @SerializedName("merchants")
    var merchants: List<Merchant?>?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("supermerchantId")
    var supermerchantId: Int?,
    @SerializedName("timestamp")
    var timestamp: Any?,
    @SerializedName("username")
    var username: String?
)