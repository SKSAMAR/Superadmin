package com.fintech.superadmin.data.apiResponse.merchant


import com.google.gson.annotations.SerializedName

data class FingPayBoardCred(
    @SerializedName("api_cred")
    var apiCred: Any?,
    @SerializedName("merchant_cred")
    var merchantCred: MerchantCred,
    @SerializedName("toBoard")
    var toBoard: Boolean
): java.io.Serializable