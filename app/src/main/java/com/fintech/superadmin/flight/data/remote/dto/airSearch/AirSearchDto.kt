package com.fintech.superadmin.flight.data.remote.dto.airSearch

import java.text.SimpleDateFormat
import java.util.*

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import com.fintech.superadmin.flight.util.common.ViewUtils.roundOffDecimal
import com.fintech.superadmin.flight.data.remote.dto.comm.CommPackDto
import kotlinx.parcelize.IgnoredOnParcel
import java.util.concurrent.TimeUnit

//import kotlinx.android.parcel.IgnoredOnParcel

@Parcelize
data class AirSearchDto(

    @field:SerializedName("Search_Key")
    val searchKey: String? = null,

    @field:SerializedName("Response_Header")
    val responseHeader: ResponseHeader? = null,

    @field:SerializedName("TripDetails")
    val tripDetails: List<TripDetailsItem?>? = null
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
data class FlightsItem(

    @field:SerializedName("Origin")
    val origin: String? = null,

    @field:SerializedName("Destination")
    val destination: String? = null,

    @field:SerializedName("Repriced")
    val repriced: Boolean? = null,

    @field:SerializedName("InventoryType")
    val inventoryType: Float? = null,

    @field:SerializedName("Flight_Key")
    val flightKey: String? = null,

    @field:SerializedName("TravelDate")
    val travelDate: String? = null,

    @field:SerializedName("Segments")
    val segments: List<SegmentsItem?>? = null,

    @field:SerializedName("Fares")
    val fares: List<FaresItem?>? = null,

    @field:SerializedName("Flight_Numbers")
    val flightNumbers: String? = null,

    @field:SerializedName("Airline_Code")
    val airlineCode: String? = null,

    @field:SerializedName("Block_Ticket_Allowed")
    val blockTicketAllowed: Boolean? = null,

    @field:SerializedName("Cached")
    val cached: Boolean? = null,

    @field:SerializedName("IsLCC")
    val isLCC: Boolean? = null,

    @field:SerializedName("GST_Entry_Allowed")
    val gSTEntryAllowed: Boolean? = null,

    @field:SerializedName("Flight_Id")
    val flightId: String? = null,

    @field:SerializedName("HasMoreClass")
    val hasMoreClass: Boolean? = null,

    @IgnoredOnParcel
    val selectedFareItem: MutableState<FaresItem?> = mutableStateOf(fares?.firstOrNull())
) : Parcelable

fun FlightsItem.toFlightsItemModel(): FlightsItemModel {
    return FlightsItemModel(
        origin,
        destination,
        repriced,
        inventoryType,
        flightKey,
        travelDate,
        segments,
        fares,
        flightNumbers,
        airlineCode
    )
}


@Parcelize
data class FlightsItemModel(

    @field:SerializedName("Origin")
    val origin: String? = null,

    @field:SerializedName("Destination")
    val destination: String? = null,

    @field:SerializedName("Repriced")
    val repriced: Boolean? = null,

    @field:SerializedName("InventoryType")
    val inventoryType: Float? = null,

    @field:SerializedName("Flight_Key")
    val flightKey: String? = null,

    @field:SerializedName("TravelDate")
    val travelDate: String? = null,

    @field:SerializedName("Segments")
    val segments: List<SegmentsItem?>? = null,

    @field:SerializedName("Fares")
    val fares: List<FaresItem?>? = null,

    @field:SerializedName("Flight_Numbers")
    val flightNumbers: String? = null,

    @field:SerializedName("Airline_Code")
    val airlineCode: String? = null,

    @field:SerializedName("Block_Ticket_Allowed")
    val blockTicketAllowed: Boolean? = null,

    @field:SerializedName("Cached")
    val cached: Boolean? = null,

    @field:SerializedName("IsLCC")
    val isLCC: Boolean? = null,

    @field:SerializedName("GST_Entry_Allowed")
    val gSTEntryAllowed: Boolean? = null,

    @field:SerializedName("Flight_Id")
    val flightId: String? = null,

    @field:SerializedName("HasMoreClass")
    val hasMoreClass: Boolean? = null,


    ) : Parcelable


fun FlightsItemModel.FlightsItem(): FlightsItem {
    return FlightsItem(
        origin,
        destination,
        repriced,
        inventoryType,
        flightKey,
        travelDate,
        segments,
        fares,
        flightNumbers,
        airlineCode
    )
}


@Parcelize
data class FaresItem(

    @field:SerializedName("Seats_Available")
    val seatsAvailable: String? = null,

    @field:SerializedName("LastFewSeats")
    val lastFewSeats: String? = null,

    @field:SerializedName("Warning")
    val warning: String? = null,

    @field:SerializedName("FareType")
    val fareType: Float? = null,

    @field:SerializedName("Food_onboard")
    val foodOnboard: String? = null,

    @field:SerializedName("Refundable")
    val refundable: Boolean? = null,

    @field:SerializedName("FareDetails")
    val fareDetails: List<FareDetailsItem?>? = null,

    @field:SerializedName("GSTMandatory")
    val gSTMandatory: Boolean? = null,

    @field:SerializedName("PromptMessage")
    val promptMessage: String? = null,

    @field:SerializedName("Fare_Id")
    val fareId: String? = null,

    @field:SerializedName("Fare_Key")
    val fareKey: String? = null,

    @field:SerializedName("ProductClass")
    val productClass: String? = null
) : Parcelable

@Parcelize
data class RescheduleChargesItem(

    @field:SerializedName("Applicablility")
    val applicablility: Float? = null,

    @field:SerializedName("PassengerType")
    val passengerType: Float? = null,

    @field:SerializedName("DurationTypeTo")
    val durationTypeTo: Float? = null,

    @field:SerializedName("Remarks")
    val remarks: String? = null,

    @field:SerializedName("ValueType")
    val valueType: Float? = null,

    @field:SerializedName("DurationFrom")
    val durationFrom: Float? = null,

    @field:SerializedName("DurationTo")
    val durationTo: Float? = null,

    @field:SerializedName("DurationTypeFrom")
    val durationTypeFrom: Float? = null,

    @field:SerializedName("Value")
    val value: String? = null,

    @field:SerializedName("OnlineServiceFee")
    val onlineServiceFee: Float? = null,

    @field:SerializedName("OfflineServiceFee")
    val offlineServiceFee: Float? = null,

    @field:SerializedName("Return_Flight")
    val returnFlight: Boolean? = null
) : Parcelable

@Parcelize
data class TripDetailsItem(

    @field:SerializedName("Flights")
    var flights: Set<FlightsItem?>? = null,

    @field:SerializedName("Trip_Id")
    val tripId: Float? = null
) : Parcelable

@Parcelize
data class FreeBaggage(

    @field:SerializedName("Hand_Baggage")
    val handBaggage: String? = null,

    @field:SerializedName("Check_In_Baggage")
    val checkInBaggage: String? = null
) : Parcelable

@Parcelize
data class CancellationChargesItem(

    @field:SerializedName("Applicablility")
    val applicablility: Float? = null,

    @field:SerializedName("PassengerType")
    val passengerType: Float? = null,

    @field:SerializedName("DurationTypeTo")
    val durationTypeTo: Float? = null,

    @field:SerializedName("Remarks")
    val remarks: String? = null,

    @field:SerializedName("ValueType")
    val valueType: Float? = null,

    @field:SerializedName("DurationFrom")
    val durationFrom: Float? = null,

    @field:SerializedName("DurationTo")
    val durationTo: Float? = null,

    @field:SerializedName("DurationTypeFrom")
    val durationTypeFrom: Float? = null,

    @field:SerializedName("Value")
    val value: String? = null,

    @field:SerializedName("OnlineServiceFee")
    val onlineServiceFee: Float? = null,

    @field:SerializedName("OfflineServiceFee")
    val offlineServiceFee: Float? = null,

    @field:SerializedName("Return_Flight")
    val returnFlight: Boolean? = null
) : Parcelable

@Parcelize
data class SegmentsItem(

    @field:SerializedName("Origin")
    val origin: String? = null,

    @field:SerializedName("Arrival_DateTime")
    val arrivalDateTime: String? = null,

    @field:SerializedName("Destination")
    val destination: String? = null,

    @field:SerializedName("Duration")
    val duration: String? = null,

    @field:SerializedName("Segment_Id")
    val segmentId: Float? = null,

    @field:SerializedName("Destination_Terminal")
    val destinationTerminal: String? = null,

    @field:SerializedName("Airline_Name")
    val airlineName: String? = null,

    @field:SerializedName("Origin_City")
    val originCity: String? = null,

    @field:SerializedName("Airline_Code")
    val airlineCode: String? = null,

    @field:SerializedName("Origin_Terminal")
    val origFloaterminal: String? = null,

    @field:SerializedName("Aircraft_Type")
    val aircraftType: String? = null,

    @field:SerializedName("Flight_Number")
    val flightNumber: String? = null,

    @field:SerializedName("Stop_Over")
    val stopOver: List<String?>? = null,

    @field:SerializedName("Destination_City")
    val destinationCity: String? = null,

    @field:SerializedName("Departure_DateTime")
    val departureDateTime: String? = null,

    @field:SerializedName("Return_Flight")
    val returnFlight: Boolean? = null,

    @field:SerializedName("Leg_Index")
    val legIndex: Float? = null
) : Parcelable

@Parcelize
data class FareDetailsItem(

    @field:SerializedName("Service_Fee_Amount")
    val serviceFeeAmount: Float? = null,

    @field:SerializedName("TDS")
    val tDS: Float? = null,

    @field:SerializedName("Total_Amount")
    val totalAmount: Float? = null,

    @field:SerializedName("Trade_Markup_Amount")
    val tradeMarkupAmount: Float? = null,

    @field:SerializedName("YQ_Amount")
    val yQAmount: Float? = null,

    @field:SerializedName("Net_Commission")
    val netCommission: Float? = null,

    @field:SerializedName("GST")
    val gST: Float? = null,

    @field:SerializedName("Basic_Amount")
    val basicAmount: Float? = null,

    @field:SerializedName("PAX_Type")
    val pAXType: Float? = null,

    @field:SerializedName("AirportTax_Amount")
    val airportTaxAmount: Float? = null,

    @field:SerializedName("Gross_Commission")
    val grossCommission: Float? = null,

    @field:SerializedName("AirportTaxes")
    val airportTaxes: List<AirportTaxesItem?>? = null,

    @field:SerializedName("Free_Baggage")
    val freeBaggage: FreeBaggage? = null,

    @field:SerializedName("Currency_Code")
    val currencyCode: String? = null,

    @field:SerializedName("FareClasses")
    val fareClasses: List<FareClassesItem?>? = null,

    @field:SerializedName("Promo_Discount")
    val promoDiscount: Float? = null,

    @field:SerializedName("RescheduleCharges")
    val rescheduleCharges: List<RescheduleChargesItem?>? = null,

    @field:SerializedName("CancellationCharges")
    val cancellationCharges: List<CancellationChargesItem?>? = null
) : Parcelable

@Parcelize
data class AirportTaxesItem(

    @field:SerializedName("Tax_Desc")
    val taxDesc: String? = null,

    @field:SerializedName("Tax_Code")
    val taxCode: String? = null,

    @field:SerializedName("Tax_Amount")
    val taxAmount: Float? = null
) : Parcelable

@Parcelize
data class FareClassesItem(

    @field:SerializedName("Class_Desc")
    val classDesc: String? = null,

    //DOUBTED
    @field:SerializedName("Privileges")
    val privileges: List<String?>? = null,

    @field:SerializedName("FareBasis")
    val fareBasis: String? = null,

    @field:SerializedName("Class_Code")
    val classCode: String? = null,

    @field:SerializedName("Segment_Id")
    val segmentId: Float? = null
) : Parcelable

fun FlightsItem.getDuration(): String? {
    try {
        var durationMilli = 0L
        val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")
        segments?.forEach { segment ->
            val startTime = dateFormat.parse(segment?.departureDateTime ?: "")
            val endTime = dateFormat.parse(segment?.arrivalDateTime ?: "")
            endTime?.let {
                startTime?.let {
                    val durationMillis = endTime.time - startTime.time
                    durationMilli += durationMillis
                }
            }
        }

        val hours = TimeUnit.MILLISECONDS.toHours(durationMilli)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMilli) % 60
        return "$hours hr $minutes min"

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun SegmentsItem?.getDuration(): String? {
    try {
        var durationMilli = 0L
        val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")
        this?.let { segment ->
            val startTime = dateFormat.parse(segment.departureDateTime ?: "")
            val endTime = dateFormat.parse(segment.arrivalDateTime ?: "")
            endTime?.let {
                startTime?.let {
                    val durationMillis = endTime.time - startTime.time
                    durationMilli += durationMillis
                }
            }
        }

        val hours = TimeUnit.MILLISECONDS.toHours(durationMilli)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMilli) % 60
        return "$hours hr $minutes min"

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun FlightsItem.calculateLayovers(): String? {
    try {
        var durationMilli = 0L

        var durationMilliEnding = 0L

        if (segments?.firstOrNull() == segments?.lastOrNull()) {
            return null
        }

        val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")
        segments?.forEach { segment ->
            val startTime = dateFormat.parse(segment?.departureDateTime ?: "")
            val endTime = dateFormat.parse(segment?.arrivalDateTime ?: "")
            endTime?.let {
                startTime?.let {
                    val durationMillis = endTime.time - startTime.time
                    durationMilli += durationMillis
                }
            }
        }

        val startTime = dateFormat.parse(segments?.firstOrNull()?.departureDateTime ?: "")
        val endTime = dateFormat.parse(segments?.lastOrNull()?.arrivalDateTime ?: "")
        endTime?.let {
            startTime?.let {
                durationMilliEnding = endTime.time - startTime.time
                val layoverMillis = durationMilliEnding - durationMilli
                val hours = TimeUnit.MILLISECONDS.toHours(layoverMillis)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(layoverMillis) % 60
                return "$hours hr $minutes min"

            }
        }


    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun FareDetailsItem.getTotalTaxes(): String? {
    try {
        val totalTaxes = this.airportTaxAmount!! + this.gST!!
        return totalTaxes.roundOffDecimal().toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun FaresItem?.getMorePrices(comm: CommPackDto?) {
    var netFare: Float
    var com: Float
    this?.let { faresItem ->
        comm?.let { comm ->
            faresItem.fareDetails?.firstOrNull()?.totalAmount?.let { totalAmount ->
                faresItem.fareDetails.firstOrNull()?.tDS?.let { tds ->
                    faresItem.fareDetails.firstOrNull()?.netCommission?.let { netCommission ->
                        comm.AMOUNT?.toFloat()?.let { commAmount ->
                            if (faresItem.fareDetails.firstOrNull()?.netCommission != 0.0f) {
                                if (comm.TYPE?.lowercase() == "percentage") {
                                    if (comm.AMOUNT_TYPE?.lowercase() == "credit") {
                                        netFare = totalAmount + tds - netCommission
                                        netFare -= (netFare / 100) * commAmount
                                        com = totalAmount - netFare
                                    }else{
                                        netFare = totalAmount + tds - netCommission
                                        netFare += (netFare / 100) * commAmount
                                        com = totalAmount - netFare
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}