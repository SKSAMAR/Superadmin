package com.fintech.superadmin.data.apiResponse.fingpay.boad


import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("activeFlag")
    var activeFlag: Int?,
    @SerializedName("cancellationCheckImages")
    var cancellationCheckImages: String?,
    @SerializedName("companyLegalName")
    var companyLegalName: String?,
    @SerializedName("companyMarketingName")
    var companyMarketingName: String?,
    @SerializedName("ekycDocuments")
    var ekycDocuments: String?,
    @SerializedName("emailId")
    var emailId: String?,
    @SerializedName("flag")
    var flag: Any?,
    @SerializedName("kyc")
    var kyc: Kyc?,
    @SerializedName("merchantAddress")
    var merchantAddress: MerchantAddress?,
    @SerializedName("merchantBranch")
    var merchantBranch: String?,
    @SerializedName("merchantCityName")
    var merchantCityName: String?,
    @SerializedName("merchantDistrictName")
    var merchantDistrictName: String?,
    @SerializedName("merchantId")
    var merchantId: Int?,
    @SerializedName("merchantLoginId")
    var merchantLoginId: String?,
    @SerializedName("merchantLoginPin")
    var merchantLoginPin: String?,
    @SerializedName("merchantName")
    var merchantName: String?,
    @SerializedName("merchantPhoneNumber")
    var merchantPhoneNumber: String?,
    @SerializedName("merchantPinCode")
    var merchantPinCode: String?,
    @SerializedName("remarks")
    var remarks: Any?,
    @SerializedName("settlement")
    var settlement: Settlement?,
    @SerializedName("shopAndPanImage")
    var shopAndPanImage: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("supermerchantId")
    var supermerchantId: Int?,
    @SerializedName("tan")
    var tan: String?
)