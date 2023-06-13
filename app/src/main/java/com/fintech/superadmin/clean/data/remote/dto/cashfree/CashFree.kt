package com.fintech.superadmin.clean.data.remote.dto.cashfree

import com.google.gson.annotations.SerializedName


data class CashFree(
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("subCode")
    val subCode: String?
)
