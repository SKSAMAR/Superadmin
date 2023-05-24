package com.fintech.superadmin.data.apiResponse

import com.google.gson.annotations.SerializedName

class AvenuPayResponse(

    @field:SerializedName("responseCode") val responseCode: String? = null,

    @field:SerializedName("responseReason") val responseReason: String? = null,

    @field:SerializedName("errorInfo") val errorInfo: ErrorInfo? = null,
)

class ErrorInfo(
    @field:SerializedName("error")
    val error: Error? = null
)


class Error(

    @field:SerializedName("errorMessage") val errorMessage: String? = null,

    @field:SerializedName("errorCode") val errorCode: String? = null
)
