package com.fintech.superadmin.data.apiResponse


import com.google.gson.annotations.SerializedName

data class SuvidhaRechargeResponse(
    @SerializedName("ERRORCODE")
    var eRRORCODE: String?,
    @SerializedName("HTTPCODE")
    var hTTPCODE: Int?,
    @SerializedName("MESSAGE")
    var mESSAGE: String?,
    @SerializedName("OPTXNID")
    var oPTXNID: String?,
    @SerializedName("REQUESTTXNID")
    var rEQUESTTXNID: String?,
    @SerializedName("STATUS")
    var sTATUS: Int = 123,
    @SerializedName("TXNNO")
    var tXNNO: Long?
)