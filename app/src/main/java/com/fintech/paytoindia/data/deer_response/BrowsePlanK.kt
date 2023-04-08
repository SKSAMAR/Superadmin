package com.fintech.paytoindia.data.deer_response


import com.google.gson.annotations.SerializedName

data class BrowsePlanK(
    @SerializedName("info")
    val info: Info,
    @SerializedName("message")
    val message: String,
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("status")
    val status: Boolean
)