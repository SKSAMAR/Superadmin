package com.fintech.superadmin.data.apiResponse.fingpay.boad


import com.google.gson.annotations.SerializedName

data class Settlement(
    @SerializedName("bankAccountName")
    var bankAccountName: String?,
    @SerializedName("bankBranchName")
    var bankBranchName: String?,
    @SerializedName("bankIfscCode")
    var bankIfscCode: String?,
    @SerializedName("companyBankAccountNumber")
    var companyBankAccountNumber: String?,
    @SerializedName("companyBankName")
    var companyBankName: String?
)