package com.fintech.superadmin.data.apiResponse.merchant


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MerchantCred(
    @SerializedName("AADHAAR")
    var aADHAAR: String?,
    @SerializedName("ID")
    var iD: String?,
    @SerializedName("IS_ICICI_KYC")
    var iSICICIKYC: String?,
    @SerializedName("MERCHANTCODE")
    var mERCHANTCODE: String?,
    @SerializedName("SUPERMERCHANT")
    var sUPERMERCHANT: String?,
    @SerializedName("PASSWORD")
    var pASSWORD: String?,
    @SerializedName("MOBILE")
    var mOBILE: String?,
    @SerializedName("NAME")
    var nAME: String?,
    @SerializedName("OWNER")
    var oWNER: String?,
    @SerializedName("OWNER_ID")
    var oWNERID: String?,
    @SerializedName("PAN")
    var pAN: String?,
    @SerializedName("PARTNERID")
    var pARTNERID: String?,
    @SerializedName("REF_NO")
    var rEFNO: String?,
    @SerializedName("REMARK")
    var rEMARK: String?,
    @SerializedName("STATUS")
    var sTATUS: String?,
    @SerializedName("TIMESTAMP")
    var tIMESTAMP: String?,
    @SerializedName("TXN_ID")
    var tXNID: String?
): Parcelable