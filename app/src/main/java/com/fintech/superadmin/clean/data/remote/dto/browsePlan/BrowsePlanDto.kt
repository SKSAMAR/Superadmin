package com.fintech.superadmin.clean.data.remote.dto.browsePlan

import com.google.gson.annotations.SerializedName

data class BrowsePlanDto(

    @field:SerializedName("TOPUP")
    val tOPUP: List<Plan>? = null,

    @field:SerializedName("STV")
    val sTV: List<Plan>? = null,

    @field:SerializedName("DATA")
    val dATA: List<Plan>? = null,

    @field:SerializedName("FRC")
    val fRC: List<Plan>? = null,

    @field:SerializedName("Romaing")
    val romaing: List<Plan>? = null,

    @field:SerializedName("SMS")
    val sMS: List<Plan>? = null,

    @field:SerializedName("RATE_CUTTER")
    val rATECUTTER: List<Plan>? = null,

    @field:SerializedName("JioPhone")
    val jioPhone: List<Plan>? = null,

    @field:SerializedName("FULLTT")
    val fULLTT: List<Plan>? = null,

    @field:SerializedName("COMBO")
    val cOMBO: List<Plan>? = null,

    @field:SerializedName("TwoG")
    val twoG: List<Plan>? = null
)


data class DynamicBrowsePlan(
    var title: String? = null,
    var planList: ArrayList<Plan?>? = null
)


data class Plan(
    @field:SerializedName("RS")
    val RS: Any? = null,
    @field:SerializedName("TYPE")
    val TYPE: Any? = null,
    @field:SerializedName("VALIDITY")
    val VALIDITY: Any? = null,
    @field:SerializedName("DESC")
    val DESC: Any? = null
)
