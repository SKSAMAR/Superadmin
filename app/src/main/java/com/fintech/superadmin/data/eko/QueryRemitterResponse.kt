package com.fintech.superadmin.data.eko

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QueryRemitterResponse(

    @field:SerializedName("response_status_id")
    val responseStatusId: Int? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("response_type_id")
    val responseTypeId: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class Data(

    @field:SerializedName("customer_id_type")
    val customerIdType: String? = null,

    @field:SerializedName("bc_available_limit")
    val bcAvailableLimit: Double? = null,

    @field:SerializedName("mobile")
    val mobile: String? = null,

    @field:SerializedName("used_limit")
    val usedLimit: Double? = null,

    @field:SerializedName("total_limit")
    val totalLimit: Double? = null,

    @field:SerializedName("available_limit")
    val availableLimit: Double? = null,

    @field:SerializedName("balance")
    val balance: String? = null,

    @field:SerializedName("user_code")
    val userCode: String? = null,

    @field:SerializedName("state_desc")
    val stateDesc: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("limit")
    val limit: List<LimitItem?>? = null,

    @field:SerializedName("currency")
    val currency: String? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("wallet_available_limit")
    val walletAvailableLimit: Double? = null,

    @field:SerializedName("customer_id")
    val customerId: String? = null
) : Parcelable

@Parcelize
data class LimitItem(

    @field:SerializedName("is_registered")
    val isRegistered: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("pipe")
    val pipe: String? = null,

    @field:SerializedName("used")
    val used: String? = null,

    @field:SerializedName("priority")
    val priority: Int? = null,

    @field:SerializedName("remaining")
    val remaining: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable
