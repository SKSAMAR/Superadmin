package com.fintech.superadmin.data.dto


import com.google.gson.annotations.SerializedName

data class PaysprintResponse(

    @SerializedName("paysprint_api_cred")
    val paysprintApiCred: PaysprintApiCred,
    @SerializedName("paysprint_merchant_cred")
    val paysprintMerchantCred: PaysprintMerchantCred,
    @SerializedName("toBoard")
    val toBoard: Boolean
): java.io.Serializable

data class MahagramResponse(
    @SerializedName("mahagram_api_cred")
    val mahagramApiCred: MahagramApiCred,
    @SerializedName("mahagram_merchant_cred")
    val mahagramMerchantCred: MahagramMerchant,
    @SerializedName("toBoard")
    val toBoard: Boolean
)