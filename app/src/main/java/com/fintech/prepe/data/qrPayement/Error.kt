package com.fintech.prepe.data.qrPayement


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code")
    val code: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("reason")
    val reason: Any,
    @SerializedName("source")
    val source: Any,
    @SerializedName("step")
    val step: String
)