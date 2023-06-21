package com.fintech.superadmin.flight.data.remote.sendDto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AirSearchSendDto(

	@field:SerializedName("TripInfo")
	var tripInfo: ArrayList<TripInfoItem> = ArrayList(),

	@field:SerializedName("Adult_Count")
	var adultCount: String? = null,

	@field:SerializedName("Travel_Type")
	var travelType: Int? = null,

	@field:SerializedName("Infant_Count")
	var infantCount: String? = null,

	@field:SerializedName("Class_Of_Travel")
	var classOfTravel: String? = null,

	@field:SerializedName("InventoryType")
	var inventoryType: Int? = null,

	@field:SerializedName("Booking_Type")
	var bookingType: Int? = null,

	@field:SerializedName("Filtered_Airline")
	var filteredAirline: List<FilteredAirlineItem?>? = null,

	@field:SerializedName("Child_Count")
	var childCount: String? = null
) : Parcelable

@Parcelize
data class TripInfoItem(

	@field:SerializedName("Origin")
	var origin: String? = null,

	@field:SerializedName("Destination")
	var destination: String? = null,

	@field:SerializedName("TravelDate")
	var travelDate: String? = null,

	@field:SerializedName("Trip_Id")
	var tripId: Int? = null
) : Parcelable

@Parcelize
data class FilteredAirlineItem(

	@field:SerializedName("Airline_Code")
	var airlineCode: String? = null
) : Parcelable
