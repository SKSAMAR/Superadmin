package com.fintech.superadmin.data.dto

data class CmsResponse(
    val status: Boolean,
    val response_code: Int,
    val redirectionUrl: String,
    val message: String
)
