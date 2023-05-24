package com.fintech.superadmin.data.apiResponse.fingpay.boad


import com.google.gson.annotations.SerializedName

data class MerchantAddress(
    @SerializedName("merchantAddress")
    var merchantAddress: String?,
    @SerializedName("merchantState")
    var merchantState: String?
)