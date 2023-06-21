package com.fintech.superadmin.flight.data.remote.dto.airFareRule

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AirFareRuleDto(

    @field:SerializedName("Response_Header")
    val responseHeader: ResponseHeader? = null,

    @field:SerializedName("FareRules")
    val fareRules: List<FareRulesItem?>? = null
) : Parcelable

@Parcelize
data class FareRulesItem(

    @field:SerializedName("FareRuleDesc")
    val fareRuleDesc: String? = null,

    @field:SerializedName("Segment_Id")
    val segmentId: String? = null,

    @field:SerializedName("FareRuleName")
    val fareRuleName: String? = null
) : Parcelable{
    override fun toString(): String {
        return "FareRulesItem(fareRuleDesc=$fareRuleDesc, segmentId=$segmentId, fareRuleName=$fareRuleName)"
    }
}

fun List<FareRulesItem?>?.getRules(): String{
    var someValue = ""
    this?.forEach {
        it?.let {
            someValue += it.toString()
        }
    }
    return someValue
}

@Parcelize
data class ResponseHeader(

    @field:SerializedName("Request_Id")
    val requestId: String? = null,

    @field:SerializedName("Error_Code")
    val errorCode: String? = null,

    @field:SerializedName("Status_Id")
    val statusId: String? = null,

    @field:SerializedName("Error_Desc")
    val errorDesc: String? = null,

    @field:SerializedName("Error_InnerException")
    val errorInnerException: String? = null
) : Parcelable
