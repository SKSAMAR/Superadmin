package com.fintech.superadmin.data.dto


import com.fintech.superadmin.util.service.KotEncryption
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class PaysprintResponse(
    @SerializedName("paysprint_api_cred")
    val paysprintApiCred: String?,
    @SerializedName("paysprint_merchant_cred")
    val paysprintMerchantCred: String?,
    @SerializedName("toBoard")
    val toBoard: Boolean
) : java.io.Serializable {

    fun getPaysprintApiCredentials(): PaysprintApiCred? {
        return try {
            val json = KotEncryption.decrypt(paysprintApiCred ?: "")
            val cred = Gson().fromJson(json, PaysprintApiCred::class.java)
            cred
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getPaysprintMerchantCredentials(): PaysprintMerchantCred? {
        return try {
            val json = KotEncryption.decrypt(paysprintMerchantCred ?: "")
            val cred = Gson().fromJson(json, PaysprintMerchantCred::class.java)
            cred
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

data class MahagramResponse(
    @SerializedName("mahagram_api_cred")
    val mahagramApiCred: String?,
    @SerializedName("mahagram_merchant_cred")
    val mahagramMerchantCred: String?,
    @SerializedName("toBoard")
    val toBoard: Boolean
) {
    fun getMahagramApiCredentials(): MahagramApiCred? {
        return try {
            val json = KotEncryption.decrypt(mahagramApiCred ?: "")
            val cred = Gson().fromJson(json, MahagramApiCred::class.java)
            cred
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getMahagramMerchantCredentials(): MahagramMerchant? {
        return try {
            val json = KotEncryption.decrypt(mahagramMerchantCred ?: "")
            val cred = Gson().fromJson(json, MahagramMerchant::class.java)
            cred
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}