package com.fintech.superadmin.clean.data.remote.dto.addMember

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MemberDataDto(

    @field:SerializedName("SUB_DATE")
    val sUBDATE: String? = null,
    @field:SerializedName("MAIN_OWNER_ID")
    val mAINOWNERID: String? = null,
    @field:SerializedName("ADHAAR")
    val aDHAAR: String? = null,
    @field:SerializedName("SUBSCRIPTION")
    val sUBSCRIPTION: String? = null,
    @field:SerializedName("STATE")
    val sTATE: String? = null,
    @field:SerializedName("EMAIL")
    val eMAIL: String? = null,
    @field:SerializedName("FIRST_NAME")
    val fIRSTNAME: String? = null,
    @field:SerializedName("LAST_NAME")
    val lASTNAME: String? = null,
    @field:SerializedName("AEPS_BAL")
    val aEPSBAL: String? = null,
    @field:SerializedName("OWNER_ID")
    val oWNERID: String? = null,
    @field:SerializedName("CITY")
    val cITY: String? = null,
    @field:SerializedName("PIN")
    val pIN: String? = null,
    @field:SerializedName("USER_TYPE")
    val uSERTYPE: String? = null,
    @field:SerializedName("DOB")
    val dOB: String? = null,
    @field:SerializedName("ADDRESS")
    val aDDRESS: String? = null,
    @field:SerializedName("US_STATUS")
    val uSSTATUS: String? = null,
    @field:SerializedName("ID")
    val iD: String? = null,
    @field:SerializedName("MAIN_OWNER")
    val mAINOWNER: String? = null,
    @field:SerializedName("PAN")
    val pAN: String? = null,
    @field:SerializedName("PARTNER_ID")
    val pARTNERID: String? = null,
    @field:SerializedName("MOBILE")
    val mOBILE: String? = null,
    @field:SerializedName("MAIN_BAL")
    val mAINBAL: String? = null,
    @field:SerializedName("PROFILE_IMG")
    val pROFILEIMG: String? = null,
    @field:SerializedName("DATE")
    val dATE: String? = null,
    @field:SerializedName("THIS_WEEK_AMOUNT")
    val tHISWEEKAMOUNT: String? = null,
    @field:SerializedName("THIS_MONTH_AMOUNT")
    val tHISMONTHAMOUNT: String? = null,
    @field:SerializedName("LAST_MONTH_AMOUNT")
    val lASTMONTHAMOUNT: String? = null,
    @field:SerializedName("LIFETIME_AMOUNT")
    val lIFETIMEAMOUNT: String? = null,

) : Parcelable{
    fun userTypeName(): String{
        try {
            if (uSERTYPE?.trim() == "1"){
                return  "Retailer"
            }
            if (uSERTYPE?.trim() == "2"){
                return  "Distributor"
            }
            if (uSERTYPE?.trim() == "3"){
                return  "Master Distributor"
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return ""
    }

    override fun toString(): String {
        return fIRSTNAME?:""
    }
}
