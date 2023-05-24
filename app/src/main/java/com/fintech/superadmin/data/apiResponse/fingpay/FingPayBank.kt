package com.fintech.superadmin.data.apiResponse.fingpay


import com.google.gson.annotations.SerializedName

data class FingPayBank(
    @SerializedName("activeFlag")
    var activeFlag: Int?,
    @SerializedName("bankName")
    var bankName: String?,
    @SerializedName("details")
    var details: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("iinno")
    var iinno: String,
    @SerializedName("remarks")
    var remarks: Any?,
    @SerializedName("timestamp")
    var timestamp: String
){
    override fun toString(): String {
        return bankName?:details?:""
    }
}