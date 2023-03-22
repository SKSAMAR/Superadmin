package com.fintech.petoindia.data.dto


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaysprintApiCred(
    @SerializedName("AUTHORISED_KEY")
    val aUTHORISEDKEY: String,
    @SerializedName("DATE")
    val dATE: String,
    @SerializedName("FIRM")
    val fIRM: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("JWT_KEY")
    val jWTKEY: String,
    @SerializedName("KEY")
    val kEY: String,
    @SerializedName("KEY_IV")
    val kEYIV: String,
    @SerializedName("MERCHANT_CODE")
    val mERCHANTCODE: String,
    @SerializedName("OWNER")
    val oWNER: String,
    @SerializedName("OWNER_ID")
    val oWNERID: String,
    @SerializedName("PARTNER_ID")
    val pARTNERID: String,
    @SerializedName("STATUS")
    val sTATUS: String,
    @SerializedName("URL")
    val uRL: String
): Serializable