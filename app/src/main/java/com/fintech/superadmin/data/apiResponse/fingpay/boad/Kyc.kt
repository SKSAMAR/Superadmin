package com.fintech.superadmin.data.apiResponse.fingpay.boad


import com.google.gson.annotations.SerializedName

data class Kyc(
    @SerializedName("aadhaarNumber")
    var aadhaarNumber: String?,
    @SerializedName("companyOrShopPan")
    var companyOrShopPan: String?,
    @SerializedName("gstinNumber")
    var gstinNumber: Any?,
    @SerializedName("userPan")
    var userPan: String?
)