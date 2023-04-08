package com.fintech.paytoindia.activities.mahagrm_bc


import com.google.gson.annotations.SerializedName

data class BCRegisterResponseItem(
    @SerializedName("Message")
    val message: String,
    @SerializedName("StatusCode")
    val statusCode: String,
    @SerializedName("Statuscode")
    val successCode: String?
)