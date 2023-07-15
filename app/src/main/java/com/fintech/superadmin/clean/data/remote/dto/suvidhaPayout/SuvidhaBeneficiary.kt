package com.fintech.superadmin.clean.data.remote.dto.suvidhaPayout

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuvidhaBeneficiary(
    @field:SerializedName("ID") val ID: String? = null,
    @field:SerializedName("BENE_ID") val BENE_ID: String? = null,
    @field:SerializedName("NAME") val NAME: String? = null,
    @field:SerializedName("ACCOUNT") val ACCOUNT: String? = null,
    @field:SerializedName("IFSC") val IFSC: String? = null,
) : Parcelable
