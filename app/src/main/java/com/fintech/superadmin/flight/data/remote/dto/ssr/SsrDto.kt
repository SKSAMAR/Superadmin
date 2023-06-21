package com.fintech.superadmin.flight.data.remote.dto.ssr

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SsrDto(

	@field:SerializedName("Response_Header")
	val responseHeader: ResponseHeader? = null,

	@field:SerializedName("SSRFlightDetails")
	val sSRFlightDetails: List<SSRFlightDetailsItem?>? = null
) : Parcelable

@Parcelize
data class ResponseHeader(

	@field:SerializedName("Request_Id")
	val requestId: String? = null,

	@field:SerializedName("Error_Code")
	val errorCode: String? = null,

	@field:SerializedName("Status_Id")
	val statusId: String? = null,

	@field:SerializedName("Error_Desc")
	val errorDesc: String? = null,

	@field:SerializedName("Error_InnerException")
	val errorInnerException: String? = null
) : Parcelable

@Parcelize
data class SSRDetailsItem(

	@field:SerializedName("ApplicablePaxTypes")
	val applicablePaxTypes: List<Int?>? = null,

	@field:SerializedName("SSR_TypeName")
	val sSRTypeName: String? = null,

	@field:SerializedName("Total_Amount")
	val totalAmount: Float? = null,

	@field:SerializedName("SSR_Key")
	val sSRKey: String? = null,

	@field:SerializedName("Segment_Id")
	val segmentId: Int? = null,

	@field:SerializedName("Segment_Wise")
	val segmentWise: Boolean? = null,

	@field:SerializedName("SSR_Type")
	val sSRType: Int? = null,

	@field:SerializedName("SSR_Code")
	val sSRCode: String? = null,

	@field:SerializedName("SSR_TypeDesc")
	val sSRTypeDesc: String? = null,

	@field:SerializedName("Flight_ID")
	val flightID: String? = null,

	@field:SerializedName("Currency_Code")
	val currencyCode: String? = null,

	@field:SerializedName("SSR_Status")
	val sSRStatus: Int? = null,

	@field:SerializedName("Leg_Index")
	val legIndex: Int? = null
) : Parcelable

@Parcelize
data class SSRFlightDetailsItem(

	@field:SerializedName("SSRDetails")
	val sSRDetails: List<SSRDetailsItem?>? = null
) : Parcelable
