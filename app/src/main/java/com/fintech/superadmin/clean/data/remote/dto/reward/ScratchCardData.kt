package com.fintech.superadmin.clean.data.remote.dto.reward

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName

data class ScratchCardData(

    @field:SerializedName("DATE")
    val dATE: String? = null,

    @field:SerializedName("AMOUNT")
    val aMOUNT: String? = null,

    @field:SerializedName("SCRATCH_STATUS")
    var sCRATCHSTATUS: Int? = null,

    @field:SerializedName("DESCRIPTION")
    val dESCRIPTION: String? = null,

    @field:SerializedName("USER_TYPE")
    val uSERTYPE: String? = null,

    @field:SerializedName("USER_ID")
    val uSERID: String? = null,

    @field:SerializedName("COUPON_REF")
    val cOUPONREF: String? = null,

    @field:SerializedName("TITLE")
    val tITLE: String? = null,

    @field:SerializedName("ID")
    val iD: String? = null,

    @field:SerializedName("TRANSACTION_TYPE")
    val tRANSACTIONTYPE: String? = null,

    @field:SerializedName("TRANS_REF")
    val tRANSREF: String? = null,

    @field:SerializedName("EXPIRY_DATE")
    val eXPIRYDATE: String? = null
)

fun ScratchCardData.toScratchCardModel(): ScratchCardModel {
    return ScratchCardModel(
        dATE = dATE,
        aMOUNT = aMOUNT,
        sCRATCHSTATUS = mutableStateOf(sCRATCHSTATUS ?: 0),
        dESCRIPTION = dESCRIPTION, uSERTYPE = uSERTYPE, uSERID = uSERID,
        cOUPONREF = cOUPONREF, tITLE = tITLE, iD = iD,
        tRANSACTIONTYPE = tRANSACTIONTYPE, tRANSREF = tRANSREF, eXPIRYDATE = eXPIRYDATE
    )
}

data class ScratchCardModel(

    val dATE: String? = null,

    val aMOUNT: String? = null,

    var sCRATCHSTATUS: MutableState<Int>,

    val dESCRIPTION: String? = null,

    val uSERTYPE: String? = null,

    val uSERID: String? = null,

    val cOUPONREF: String? = null,

    val tITLE: String? = null,

    val iD: String? = null,

    val tRANSACTIONTYPE: String? = null,

    val tRANSREF: String? = null,

    val eXPIRYDATE: String? = null,
)


fun ScratchCardModel.toScratchCardData(): ScratchCardData {
    return ScratchCardData(
        dATE = dATE,
        aMOUNT = aMOUNT,
        sCRATCHSTATUS = sCRATCHSTATUS.value,
        dESCRIPTION = dESCRIPTION, uSERTYPE = uSERTYPE, uSERID = uSERID,
        cOUPONREF = cOUPONREF, tITLE = tITLE, iD = iD,
        tRANSACTIONTYPE = tRANSACTIONTYPE, tRANSREF = tRANSREF, eXPIRYDATE = eXPIRYDATE
    )
}