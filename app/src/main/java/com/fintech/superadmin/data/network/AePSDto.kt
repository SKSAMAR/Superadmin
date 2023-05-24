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

    val infoList: List<String?>? = emptyList(),
) : java.io.Serializable {
    override fun toString(): String {
        return "AePSDto(responseCode=$responseCode, clientrefno=$clientrefno, balanceamount=$balanceamount, message=$message, ackno=$ackno, lastAadhar=$lastAadhar, datetime=$datetime, name=$name, ministatement=$ministatement, bankrrn=$bankrrn, bankiin=$bankiin, errorcode=$errorcode, status=$status, amount=$amount, mobile=$mobile)"
    }
}

data class MinistatementItem(

    @field:SerializedName("date")
    val date: Any? = null,

    @field:SerializedName("amount")
    val amount: Any? = null,

    @field:SerializedName("narration")
    val narration: Any? = null,

    @field:SerializedName("txnType")
    val txnType: Any? = null
) : java.io.Serializable {
    override fun toString(): String {
        return "MinistatementItem(date=$date, amount=$amount, narration=$narration, txnType=$txnType)"
    }
}
