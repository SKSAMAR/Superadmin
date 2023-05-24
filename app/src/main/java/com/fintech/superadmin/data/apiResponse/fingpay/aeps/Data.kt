package com.fintech.superadmin.data.apiResponse.fingpay.aeps


import com.fintech.superadmin.data.network.MinistatementItem
import com.google.gson.annotations.SerializedName
import com.fintech.superadmin.data.network.responses.MiniStatementData

data class Data(
    @SerializedName("agentId")
    var agentId: Int?,
    @SerializedName("balanceAmount")
    var balanceAmount: Double?,
    @SerializedName("bankAccountNumber")
    var bankAccountNumber: String?,
    @SerializedName("bankName")
    var bankName: String?,
    @SerializedName("bankRRN")
    var bankRRN: String?,
    @SerializedName("bcLocation")
    var bcLocation: String?,
    @SerializedName("bcName")
    var bcName: String?,
    @SerializedName("biTxnType")
    var biTxnType: String?,
    @SerializedName("customerAadhaarNumber")
    var customerAadhaarNumber: String?,
    @SerializedName("customerName")
    var customerName: String?,
    @SerializedName("demandSheetId")
    var demandSheetId: String?,
    @SerializedName("errorCode")
    var errorCode: String?,
    @SerializedName("errorMessage")
    var errorMessage: String?,
    @SerializedName("fpTransactionId")
    var fpTransactionId: String?,
    @SerializedName("fpkAgentId")
    var fpkAgentId: String?,
    @SerializedName("hindiErrorMessage")
    var hindiErrorMessage: String?,
    @SerializedName("ifscCode")
    var ifscCode: String?,
    @SerializedName("internalReferenceNumber")
    var internalReferenceNumber: String?,
    @SerializedName("issuerBank")
    var issuerBank: String?,
    @SerializedName("loanAccNo")
    var loanAccNo: String?,
    @SerializedName("merchantTransactionId")
    var merchantTransactionId: String?,
    @SerializedName("merchantTxnId")
    var merchantTxnId: String?,
    @SerializedName("miniOffusFlag")
    var miniOffusFlag: Boolean?,
    @SerializedName("miniOffusStatementStructureModel")
    var miniOffusStatementStructureModel: List<String?>?,
    @SerializedName("miniStatementStructureModel")
    var miniStatementStructureModel: List<MiniStatementData?>?,
    @SerializedName("mobileNumber")
    var mobileNumber: String?,
    @SerializedName("prospectNumber")
    var prospectNumber: String?,
    @SerializedName("requestTransactionTime")
    var requestTransactionTime: String?,
    @SerializedName("responseCode")
    var responseCode: String?,
    @SerializedName("rrn")
    var rrn: String?,
    @SerializedName("stan")
    var stan: String?,
    @SerializedName("strMiniStatementBalance")
    var strMiniStatementBalance: String?,
    @SerializedName("subVillageName")
    var subVillageName: String?,
    @SerializedName("terminalId")
    var terminalId: String?,
    @SerializedName("transactionAmount")
    var transactionAmount: Double?,
    @SerializedName("transactionRemark")
    var transactionRemark: String?,
    @SerializedName("transactionStatus")
    var transactionStatus: String?,
    @SerializedName("transactionTime")
    var transactionTime: String?,
    @SerializedName("transactionType")
    var transactionType: String?,
    @SerializedName("uidaiAuthCode")
    var uidaiAuthCode: String?,
    @SerializedName("urnId")
    var urnId: String?,
    @SerializedName("userProfileResponseModel")
    var userProfileResponseModel: String?
) : java.io.Serializable


fun MiniStatementData?.toMinistatementItem(): MinistatementItem? {
    this?.let {
        return MinistatementItem(date, amount, narration, txnType)
    }
    return null
}

fun List<MiniStatementData?>?.toMinistatementListItem(): List<MinistatementItem?>? {
    if (this.isNullOrEmpty()) {
        return null;
    }
    return map { it.toMinistatementItem() }
}

fun List<String?>?.toMinistatementListItemString(): List<MinistatementItem?>? {
    this?.let {

        val list = ArrayList<MinistatementItem>()

         it.forEach {
            it?.let {
               //val data =  it.split(it, ",")
                try {
                    list.add(MinistatementItem(date = it, null, null, null))
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        return list
    }
    return null
}