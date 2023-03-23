package com.fintech.payware.data.dto


import com.google.gson.annotations.SerializedName

data class MahagramApiCred(
    @SerializedName("ChannelPartnerID")
    val channelPartnerID: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("SaltKey")
    val saltKey: String,
    @SerializedName("SecretKey")
    val secretKey: String
)