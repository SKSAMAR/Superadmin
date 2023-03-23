package com.fintech.payware.data.dto


import com.google.gson.annotations.SerializedName

data class CreditCardHistory(
    @SerializedName("AMOUNT")
    var aMOUNT: String?,
    @SerializedName("CARD_NUM")
    var cARDNUM: String?,
    @SerializedName("CARD_TYPE")
    var cARDTYPE: String?,
    @SerializedName("REFFRENCE_ID")
    var rEFFRENCEID: String?,
    @SerializedName("STATUS")
    var sTATUS: String?
)