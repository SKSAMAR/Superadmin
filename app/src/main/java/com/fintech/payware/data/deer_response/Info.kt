package com.fintech.payware.data.deer_response


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("COMBO")
    val cOMBO: List<Plan>,
    @SerializedName("FULLTT")
    val fULLTT: List<Plan>,
    @SerializedName("2G")
    val g: List<Plan>,
    @SerializedName("3G/4G")
    val g4G: List<Plan>,
    @SerializedName("RATE CUTTER")
    val rATECUTTER: List<Plan>,
    @SerializedName("Romaing")
    val romaing: List<Plan>,
    @SerializedName("SMS")
    val sMS: List<Plan>,
    @SerializedName("TOPUP")
    val tOPUP: List<Plan>
)