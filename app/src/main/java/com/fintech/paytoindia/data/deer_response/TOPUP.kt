package com.fintech.paytoindia.data.deer_response


import com.google.gson.annotations.SerializedName

data class TOPUP(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("last_update")
    val lastUpdate: String,
    @SerializedName("rs")
    val rs: String,
    @SerializedName("validity")
    val validity: String
)