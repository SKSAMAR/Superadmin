package com.fintech.superadmin.data.apiResponse.merchant


import com.fintech.superadmin.data.dto.MahagramApiCred
import com.fintech.superadmin.util.service.KotEncryption
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class FingPayBoardCred(
    @SerializedName("api_cred")
    var apiCred: Any?,
    @SerializedName("merchant_cred")
    var merchantCred: String?,
    @SerializedName("toBoard")
    var toBoard: Boolean
): java.io.Serializable{

    fun getMerchantCredentials(): MerchantCred?{
        return try {
            val json = KotEncryption.decrypt(merchantCred?:"")
            val cred = Gson().fromJson(json, MerchantCred::class.java)
            cred
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}