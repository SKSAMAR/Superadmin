package com.fintech.superadmin.flight.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AirPortDto(

    @field:SerializedName("AIRPORT")
    val aIRPORT: String? = null,

    @field:SerializedName("CITY")
    val cITY: String? = null,

    @field:SerializedName("COUNTRY")
    val cOUNTRY: String? = null,

    @field:SerializedName("AIRPORT_CODE")
    val aIRPORTCODE: String? = null,

    @field:SerializedName("ID")
    val iD: String? = null,

    @field:SerializedName("COUNTRY_CODE")
    val cOUNTRYCODE: String? = null
) : Parcelable
