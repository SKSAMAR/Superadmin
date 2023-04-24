package com.fintech.superadmin.data.qrPayement


import com.google.gson.annotations.SerializedName

data class StatusDetails(
    @SerializedName("description")
    val description: Any,
    @SerializedName("reason")
    val reason: Any,
    @SerializedName("source")
    val source: Any
)