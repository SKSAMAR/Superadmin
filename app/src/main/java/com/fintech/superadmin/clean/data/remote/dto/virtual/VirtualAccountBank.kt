package com.fintech.superadmin.clean.data.remote.dto.virtual


import com.google.gson.annotations.SerializedName

data class VirtualAccountBank(
    @SerializedName("ADDED_ACC")
    var aDDEDACC: String?,
    @SerializedName("ADDED_IFSC")
    var aDDEDIFSC: String?,
    @SerializedName("BANK_ID")
    var bANKID: String?,
    @SerializedName("CUSTOMER_ID")
    var cUSTOMERID: String?,
    @SerializedName("ID")
    var iD: String?,
    @SerializedName("RECIEVED_ACC")
    var rECIEVEDACC: String?,
    @SerializedName("RECIEVED_IFSC")
    var rECIEVEDIFSC: String?,
    @SerializedName("TIME")
    var tIME: String?,
    @SerializedName("USER_ID")
    var uSERID: String?,
    @SerializedName("VIRTUAL_ID")
    var vIRTUALID: String?,
    @SerializedName("VPA_ID")
    var vPA_ID: String?,
    @SerializedName("VPA_ADDRESS")
    var vPA_ADDRESS: String?
)