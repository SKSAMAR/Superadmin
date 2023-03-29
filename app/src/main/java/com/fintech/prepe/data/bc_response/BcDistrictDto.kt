package com.fintech.prepe.data.bc_response


import com.google.gson.annotations.SerializedName

data class BcDistrictDto(
    @SerializedName("districtid")
    val districtid: Int,
    @SerializedName("districtname")
    val districtname: String
){
    override fun toString(): String {
        return districtname
    }
}