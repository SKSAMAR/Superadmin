package com.fintech.superadmin.data.apiResponse.fingpay.aeps


import com.fintech.superadmin.data.network.AePSDto
import com.google.gson.annotations.SerializedName

data class AePsResponse(
    @SerializedName("data")
    var data: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("statusCode")
    var statusCode: Int?
) : java.io.Serializable {

    fun toAePsDto(r: AePsResponse?): AePSDto? {
        return toAePsDtoChange(r)
    }
}

fun toAePsDtoChange(r: AePsResponse?): AePSDto {


    return AePSDto(
        responseCode = if (r?.statusCode == 10000) 1 else r?.statusCode ?: 409,
        message = r?.message ?: "",
        status = r?.statusCode == 10000,
        clientrefno = r?.data?.merchantTxnId ?: "",
        balanceamount = r?.data?.balanceAmount ?: "",
        ackno = r?.data?.fpTransactionId ?: "",
        lastAadhar = r?.data?.customerAadhaarNumber ?: "",
        datetime = r?.data?.requestTransactionTime ?:r?.data?.transactionTime ?: "",
        name = r?.data?.bcName ?: r?.data?.customerName ?: "",
        ministatement = r?.data?.miniStatementStructureModel?.toMinistatementListItem() ?: emptyList(),
        bankiin = r?.data?.terminalId ?: "",
        bankrrn = r?.data?.bankRRN ?:r?.data?.rrn ?: "",
        errorcode = r?.statusCode ?: 3423,
        amount = r?.data?.transactionAmount ?: "",
        mobile = r?.data?.mobileNumber?: "",
        infoList = r?.data?.miniOffusStatementStructureModel ?: emptyList()
    )
}