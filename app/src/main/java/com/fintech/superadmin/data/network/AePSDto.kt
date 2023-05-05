package com.fintech.superadmin.data.network

import com.google.gson.annotations.SerializedName

data class AePSDto(

    @field:SerializedName("response_code")
    val responseCode: Int? = null,

    @field:SerializedName("clientrefno")
    val clientrefno: Any? = null,

    @field:SerializedName("balanceamount")
    val balanceamount: Any? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("ackno")
    val ackno: Any? = null,

    @field:SerializedName("last_aadhar")
    val lastAadhar: Any? = null,

    @field:SerializedName("datetime")
    val datetime: Any? = null,

    @field:SerializedName("name")
    val name: Any? = null,

    @field:SerializedName("ministatement")
    val ministatement: List<MinistatementItem?>? = null,

    @field:SerializedName("bankrrn")
    val bankrrn: Any? = null,

    @field:SerializedName("bankiin")
    val bankiin: Any? = null,

    @field:SerializedName("errorcode")
    val errorcode: Any? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,


    @field:SerializedName("amount")
    val amount: Any? = null,


    @field:SerializedName("mobile")
    val mobile: Any? = null,

    ) : java.io.Serializable

data class MinistatementItem(

    @field:SerializedName("date")
    val date: Any? = null,

    @field:SerializedName("amount")
    val amount: Any? = null,

    @field:SerializedName("narration")
    val narration: Any? = null,

    @field:SerializedName("txnType")
    val txnType: Any? = null
) : java.io.Serializable
