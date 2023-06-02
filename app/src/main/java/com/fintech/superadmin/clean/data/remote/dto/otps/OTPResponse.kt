package com.fintech.superadmin.clean.data.remote.dto.otps

import com.google.gson.annotations.SerializedName

data class OTPResponse(
    @field:SerializedName("response_code")
    val response_code: Int,
    @field:SerializedName("smsotpst")
    val smsotpst: String,
    @field:SerializedName("OTPHASH")
    val OTPHASH: String,
)
