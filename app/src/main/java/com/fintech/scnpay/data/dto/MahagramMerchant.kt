package com.fintech.scnpay.data.dto


import com.google.gson.annotations.SerializedName

data class MahagramMerchant(
    @SerializedName("address")
    val address: String,
    @SerializedName("address_proof")
    val addressProof: String,
    @SerializedName("alternate_num")
    val alternateNum: String,
    @SerializedName("area_population")
    val areaPopulation: String,
    @SerializedName("BC_ID")
    val bCID: String,
    @SerializedName("block")
    val block: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("f_name")
    val fName: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("id_proof")
    val idProof: String,
    @SerializedName("l_name")
    val lName: String,
    @SerializedName("landmark")
    val landmark: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("mohoalla")
    val mohoalla: String,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("num")
    val num: String,
    @SerializedName("pan_number")
    val panNumber: String,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("psport_pic")
    val psportPic: String,
    @SerializedName("qualification")
    val qualification: String,
    @SerializedName("shop_name")
    val shopName: String,
    @SerializedName("shop_photo")
    val shopPhoto: String,
    @SerializedName("shop_type")
    val shopType: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("US_ID")
    val uSID: String,
    @SerializedName("US_TYPE")
    val uSTYPE: String
)