package com.fintech.superadmin.data.bc_response


import com.google.gson.annotations.SerializedName

data class BcStateDto(
    @SerializedName("statecode")
    val statecode: String,
    @SerializedName("stateid")
    val stateid: Int,
    @SerializedName("statename")
    val statename: String
){
    override fun toString(): String {
        return statename
    }
}