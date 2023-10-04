package com.fintech.superadmin.data.api_response.billavenue.lulu

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


@Parcelize
data class ParamInfoItem(

	@field:SerializedName("dataType")
	val dataType: String? = null,

	@field:SerializedName("paramName")
	val paramName: String? = null,

	@field:SerializedName("isOptional")
	val isOptional: Boolean? = null
) : Parcelable

@Parcelize
data class BillerInputParams(

	@field:SerializedName("paramInfo")
	val paramInfo: List<ParamInfoItem?>? = null
) : Parcelable

@Parcelize
data class BillerInfoResponse(

	@field:SerializedName("biller")
	val biller: Biller? = null,

	@field:SerializedName("responseCode")
	val responseCode: Int? = null
) : Parcelable

@Parcelize
data class BillersListItem(

	@field:SerializedName("BILLER_ID")
	val bILLERID: String? = null,

	@field:SerializedName("MAIN_OWNER_ID")
	val mAINOWNERID: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("MAIN_OWNER")
	val mAINOWNER: String? = null,

	@field:SerializedName("RESPONSE")
	val rESPONSE: RESPONSE? = null
) : Parcelable

@Parcelize
data class RESPONSE(

	@field:SerializedName("billerInfoResponse")
	val billerInfoResponse: BillerInfoResponse? = null
) : Parcelable

@Parcelize
data class Biller(

	@field:SerializedName("billerCategory")
	val billerCategory: String? = null,

	@field:SerializedName("billerInputParams")
	val billerInputParams: BillerInputParams? = null,

	@field:SerializedName("billerId")
	val billerId: String? = null,

	@field:SerializedName("billerPaymentExactness")
	val billerPaymentExactness: String? = null,

	@field:SerializedName("billerFetchRequiremet")
	val billerFetchRequiremet: String? = null,

	@field:SerializedName("billerAmountOptions")
	val billerAmountOptions: String? = null,

	@field:SerializedName("billerPaymentModes")
	val billerPaymentModes: String? = null,

	@field:SerializedName("billerCoverage")
	val billerCoverage: String? = null,

	@field:SerializedName("billerName")
	val billerName: String? = null,

	@field:SerializedName("billerAdhoc")
	val billerAdhoc: Boolean? = null
) : Parcelable
