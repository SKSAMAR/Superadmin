package com.fintech.superadmin.flight.data.remote.dto.seatMap

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

@Parcelize
data class AirSeatMapDto(

	@field:SerializedName("AirSeatMaps")
	val airSeatMaps: List<AirSeatMapsItem?>? = null,

	@field:SerializedName("Response_Header")
	val responseHeader: ResponseHeader? = null
) : Parcelable

@Parcelize
data class SeatSegmentsItem(

	@field:SerializedName("Seat_Row")
	val seatRow: List<SeatRowItem?>? = null,

	@field:SerializedName("Leg_Index")
	val legIndex: Int? = null
) : Parcelable

@Parcelize
data class AirSeatMapsItem(

	@field:SerializedName("Seat_Segments")
	val seatSegments: List<SeatSegmentsItem?>? = null,

	@field:SerializedName("Flight_Id")
	val flightId: String? = null
) : Parcelable

@Parcelize
data class SeatRowItem(

	@field:SerializedName("Seat_Details")
	val seatDetails: List<SeatDetailsItem?>? = null
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
data class SeatDetailsItem(

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

fun SeatDetailsItem?.getSeatStatus(): SeatStatus{
	try {

		if (this?.sSRStatus == 0){
			return SeatStatus.ISLE
		}
		if (this?.sSRStatus == 1){
			return SeatStatus.AVAILABLE
		}
		if (this?.sSRStatus == 2){
			return SeatStatus.BLOCKED
		}
		if (this?.sSRStatus == 3){
			return SeatStatus.BOOKED
		}

	}catch (e: Exception){
		e.printStackTrace()
	}
	return SeatStatus.ISLE
}

fun SeatDetailsItem?.getSeatColor(): Color{
	try {

		if (this?.sSRStatus == 0){
			return Color.Gray
		}
		if (this?.sSRStatus == 1){
			return Color.Transparent
		}
		if (this?.sSRStatus == 2){
			return Color.Yellow
		}
		if (this?.sSRStatus == 3){
			return Color.Red
		}

	}catch (e: Exception){
		e.printStackTrace()
	}
	return Color.Gray
}

fun SeatDetailsItem?.getSeatBorderColor(): Color{
	try {

		if (this?.sSRStatus == 0){
			return Color.Gray
		}
		if (this?.sSRStatus == 1){
			return Color.Green
		}
		if (this?.sSRStatus == 2){
			return Color.Yellow
		}
		if (this?.sSRStatus == 3){
			return Color.Red
		}

	}catch (e: Exception){
		e.printStackTrace()
	}
	return Color.Gray
}

enum class SeatStatus{
	ISLE,
	AVAILABLE,
	BLOCKED,
	BOOKED
}
