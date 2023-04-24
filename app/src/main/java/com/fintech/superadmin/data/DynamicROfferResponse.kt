package com.fintech.superadmin.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DynamicROfferResponse(

    @field:SerializedName("response_code")
    val responseCode: Int? = null,

    @field:SerializedName("data")
    val data: List<ROfferPlan?>? = null,

    @field:SerializedName("message")
    val message: String? = null

) : Parcelable

@Parcelize
data class ROfferPlan(

    @field:SerializedName("RS")
    val rS: String? = null,

    @field:SerializedName("DESC")
    val dESC: String? = null
) : Parcelable