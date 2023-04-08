package com.fintech.paytoindia.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaysprintMerchantCred(
    @SerializedName("ID")
    val iD: String,
    @SerializedName("IS_ICICI_KYC")
    val iSICICIKYC: String,
    @SerializedName("MERCHANTCODE")
    val mERCHANTCODE: String,
    @SerializedName("MOBILE")
    val mOBILE: String,
    @SerializedName("PARTNERID")
    val pARTNERID: String,
    @SerializedName("REF_NO")
    val rEFNO: String,
    @SerializedName("STATUS")
    val sTATUS: String,
    @SerializedName("TIMESTAMP")
    val tIMESTAMP: String,
    @SerializedName("TXN_ID")
    val tXNID: String,
    @SerializedName("TYPE")
    val tYPE: String,
    @SerializedName("URL")
    val uRL: String
): Serializable