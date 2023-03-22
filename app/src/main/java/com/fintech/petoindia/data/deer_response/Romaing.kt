package com.fintech.petoindia.data.deer_response


import com.google.gson.annotations.SerializedName

data class Romaing(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("last_update")
    val lastUpdate: String,
    @SerializedName("rs")
    val rs: String,
    @SerializedName("validity")
    val validity: String
)