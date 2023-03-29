package com.fintech.prepe.data.deer_response


import com.google.gson.annotations.SerializedName

data class G(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("last_update")
    val lastUpdate: String,
    @SerializedName("rs")
    val rs: String,
    @SerializedName("validity")
    val validity: String
)