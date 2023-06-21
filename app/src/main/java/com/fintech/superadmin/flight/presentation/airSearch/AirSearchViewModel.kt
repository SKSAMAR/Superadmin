package com.fintech.superadmin.flight.presentation.airSearch

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import com.fintech.superadmin.flight.data.remote.FlightApi
import com.fintech.superadmin.flight.data.remote.dto.airFareRule.getRules
import com.fintech.superadmin.flight.data.remote.dto.airSearch.AirSearchDto
import com.fintech.superadmin.flight.data.remote.dto.airSearch.FlightsItem
import com.fintech.superadmin.flight.data.remote.dto.comm.CommPackDto
import com.fintech.superadmin.flight.data.remote.dto.toFilteredAirlineItem
import com.fintech.superadmin.flight.data.remote.sendDto.AirSearchSendDto
import com.fintech.superadmin.flight.data.remote.sendDto.FilteredAirlineItem
import com.fintech.superadmin.flight.data.remote.sendDto.TripInfoItem
import com.fintech.superadmin.flight.domain.ScreenState
import com.fintech.superadmin.flight.domain.state.SharedState.adult
import com.fintech.superadmin.flight.domain.state.SharedState.child
import com.fintech.superadmin.flight.domain.state.SharedState.infant
import com.fintech.superadmin.flight.domain.state.SharedState.multiCityTripList
import com.fintech.superadmin.flight.domain.state.SharedState.selectedSeatClass
import com.fintech.superadmin.flight.domain.state.SharedState.selectedTripValue
import com.fintech.superadmin.flight.domain.state.selectedAirLines
import com.fintech.superadmin.flight.presentation.airSearch.component.AirFlightConfirmContent
import com.fintech.superadmin.flight.presentation.common.BaseViewModel
import com.fintech.superadmin.flight.presentation.common.ContentComposableDialog
import com.fintech.superadmin.flight.presentation.home.component.toTripInfoItem
import com.fintech.superadmin.flight.presentation.travellers.TravellersActivity
import com.fintech.superadmin.flight.util.common.Constant
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class AirSearchViewModel
@Inject constructor(private val api: FlightApi) : BaseViewModel<AirSearchDto?>() {


    var flights = mutableStateListOf<FlightsItem?>()

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val startDate = currentDate.plusDays(1)

    @RequiresApi(Build.VERSION_CODES.O)
    val endDate = currentDate.plusDays(500)

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    @RequiresApi(Build.VERSION_CODES.O)
    var selection = mutableStateOf<LocalDate?>(
        LocalDate.parse(
            multiCityTripList.firstOrNull()?.departDateModel?.value?.classicDate,
            formatter
        )
    )

    var commPack by mutableStateOf<CommPackDto?>(null)
    var changesToFilter by mutableStateOf(false)
    var commissionDisplay by mutableStateOf(false)
    var netFareDisplay by mutableStateOf(false)
    var nonStop by mutableStateOf(false)
    var oneStop by mutableStateOf(false)
    var twoStop by mutableStateOf(false)
    var threeStop by mutableStateOf(false)
    var refundable by mutableStateOf(false)

    var nonStopCount by mutableStateOf(0)
    var oneStopCount by mutableStateOf(0)
    var twoStopCount by mutableStateOf(0)
    var threeStopCount by mutableStateOf(0)
    var refundableCount by mutableStateOf(0)

    var availableAirlines = mutableStateMapOf<String, AirlinesFilters>()


    fun searchFlight() {
        var allAirLines = selectedAirLines.map { s -> s.value.toFilteredAirlineItem() }
        if (allAirLines.isEmpty()) {
            allAirLines = listOf(FilteredAirlineItem(airlineCode = ""))
        }

        val airSearch = AirSearchSendDto(
            bookingType = selectedTripValue.id,
            travelType = 0,
            adultCount = adult.toString(),
            childCount = child.toString(),
            infantCount = infant.toString(),
            inventoryType = 0,
            filteredAirline = allAirLines,
            classOfTravel = selectedSeatClass.id.toString()
        )
        when (selectedTripValue.id) {
            0 -> {
                multiCityTripList.firstOrNull()?.let {
                    if (it.fromAirPortDto.value == null) {
                        displayDialogFailure("Select a valid Departure Airport")
                        return
                    } else if (it.toAirPortDto.value == null) {
                        displayDialogFailure("Select a valid Destination Airport")
                        return
                    } else if (it.departDateModel.value == null) {
                        displayDialogFailure("Select a valid Departure Date")
                        return
                    } else {
                        if (it.fromAirPortDto.value?.cOUNTRYCODE?.trim()?.uppercase() != "IN") {
                            airSearch.travelType = 1
                        }
                        if (it.toAirPortDto.value?.cOUNTRYCODE?.trim()?.uppercase() != "IN") {
                            airSearch.travelType = 1
                        }
                        airSearch.tripInfo.add(it.toTripInfoItem(tripId = 0))

                    }
                }
            }
            1 -> {
                multiCityTripList.firstOrNull()?.let {
                    if (it.fromAirPortDto.value == null) {
                        displayDialogFailure("Select a valid Departure Airport")
                        return
                    } else if (it.toAirPortDto.value == null) {
                        displayDialogFailure("Select a valid Destination Airport")
                        return
                    } else if (it.departDateModel.value == null) {
                        displayDialogFailure("Select a valid Departure Date")
                        return
                    } else if (it.arrivalDateModel.value == null) {
                        displayDialogFailure("Select a valid Arrival Date")
                        return
                    } else {
                        if (it.fromAirPortDto.value?.cOUNTRYCODE?.trim()?.uppercase() != "IN") {
                            airSearch.travelType = 1
                        }
                        if (it.toAirPortDto.value?.cOUNTRYCODE?.trim()?.uppercase() != "IN") {
                            airSearch.travelType = 1
                        }
                        airSearch.tripInfo.add(it.toTripInfoItem(tripId = 0))
                        airSearch.tripInfo.add(
                            TripInfoItem(
                                origin = it.toAirPortDto.value?.aIRPORTCODE,
                                destination = it.fromAirPortDto.value?.aIRPORTCODE,
                                travelDate = it.arrivalDateModel.value?.classicDate,
                                tripId = 1
                            )
                        )
                    }

                }
            }
            2 -> {

                multiCityTripList.forEach {
                    if (it.fromAirPortDto.value == null) {
                        displayDialogFailure("Select a valid Departure Airport")
                        return
                    } else if (it.toAirPortDto.value == null) {
                        displayDialogFailure("Select a valid Destination Airport")
                        return
                    } else if (it.departDateModel.value == null) {
                        displayDialogFailure("Select a valid Departure Date")
                        return
                    } else {
                        if (it.fromAirPortDto.value?.cOUNTRYCODE?.trim()?.uppercase() != "IN") {
                            airSearch.travelType = 1
                        }
                        if (it.toAirPortDto.value?.cOUNTRYCODE?.trim()?.uppercase() != "IN") {
                            airSearch.travelType = 1
                        }
                        airSearch.tripInfo.add(it.toTripInfoItem(tripId = 0))
                    }
                }
            }
        }
        getCommissionData(airSearch.travelType)
        val flightSearch = Gson().toJson(airSearch)
        getFlightsContent(type = flightSearch)
    }


    private fun getFlightsContent(type: String) {
        _state.value = ScreenState(isLoading = true)
        api.getAirSearch(BookableData = type)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.responseHeader?.errorCode == "0000" && it.responseHeader.errorDesc == "SUCCESS") {
                    if (!it.tripDetails?.firstOrNull()?.flights.isNullOrEmpty()) {
                        _state.value = ScreenState(receivedResponse = it)
                        availableAirlines.clear()
                        it.tripDetails?.forEach { tripDetails ->
                            tripDetails?.flights?.forEach { item ->
                                item?.segments?.forEach { segmentsItem ->
                                    segmentsItem?.airlineName?.trim()?.uppercase()
                                        ?.let { airlineName ->
                                            val count = tripDetails.flights?.count {
                                                it?.segments?.firstOrNull()?.airlineName?.trim()
                                                    ?.uppercase() == airlineName
                                            }
                                            val data = airlineName.toAirLinesFilter(count = count)
                                            availableAirlines[airlineName] = data
                                        }
                                }
                            }
                        }
                        flights.clear()
                        doTheFilteration()
                    } else {
                        _state.value = ScreenState(error = "No Flight Found")
                    }
                } else {
                    _state.value = ScreenState(error = it.responseHeader?.errorDesc ?: "Some Error")
                }
            }, {
                _state.value = ScreenState(error = it.localizedMessage ?: "Some Error")
            })
    }


    fun selectFlight(context: Context, flightsItem: FlightsItem) {
        _state.value.content = {
            ContentComposableDialog(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {
                AirFlightConfirmContent(item = flightsItem,
                    onFlightsItemSelect = {
                        _state.value.baseDialogVisible.value = false
                        it?.let {
//                            getAirFareRule(it)

                            val intent = Intent(context, TravellersActivity::class.java)
                            intent.putExtra(Constant.FlightsItem, it)
                            intent.putExtra(
                                Constant.SearchKey,
                                _state.value.receivedResponse?.searchKey ?: ""
                            )
                            context.startActivity(intent)

                        }
                    }
                )
            })
        }
        _state.value.baseDialogVisible.value = true
    }

    private fun getAirFareRule(flight: FlightsItem) {
        displayDialogLoading("Please.. wait")
        api.getAirFareRules(
            fareid = flight.fares?.firstOrNull()?.fareId ?: "",
            fkey = flight.flightKey ?: "",
            skey = _state.value.receivedResponse?.searchKey ?: ""
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dismissDialog()
                if (it.responseHeader?.errorCode == "0000" && it.responseHeader.errorDesc == "SUCCESS") {
                    displayDialogSuccess(it.fareRules.getRules())
                } else {
                    displayDialogFailure(it.responseHeader?.errorDesc)
                }
            }, {
                displayDialogFailure(it.localizedMessage ?: "Some Error")
            })
    }


    fun doTheFilteration() {
        var anyFilterUsed = false
        try {

            nonStopCount = 0
            oneStopCount = 0
            twoStopCount = 0
            threeStopCount = 0
            refundableCount = 0
            val nonStopList = ArrayList<FlightsItem?>()
            val oneStopList = ArrayList<FlightsItem?>()
            val twoStopList = ArrayList<FlightsItem?>()
            val threeStopList = ArrayList<FlightsItem?>()
            var myFilterList = ArrayList<FlightsItem?>()
            val secondFinalList = ArrayList<FlightsItem?>()
            val airlinesFilterList = ArrayList<FlightsItem?>()
            state.value.receivedResponse?.tripDetails?.firstOrNull()?.flights?.let {
                myFilterList.addAll(it)
            }
            state.value.receivedResponse?.tripDetails?.firstOrNull()?.flights?.forEach {
                it?.let { flight ->
                    if (!flight.segments.isNullOrEmpty() && flight.segments.size > 1) {
                        if (flight.segments.size - 1 == 1) {
                            oneStopCount++
                            if (oneStop) {
                                anyFilterUsed = true
                                oneStopList.add(flight)//One Stop
                            }
                        } else if (flight.segments.size - 1 == 2) {
                            if (twoStop) {
                                twoStopCount++
                                anyFilterUsed = true
                                twoStopList.add(flight)//Two Stop
                            }
                        } else if (flight.segments.size - 1 == 3) {
                            threeStopCount++
                            if (threeStop) {
                                anyFilterUsed = true
                                threeStopList.add(flight)//Three Stop
                            }
                        }
                    } else {
                        nonStopCount++
                        if (nonStop) {
                            anyFilterUsed = true
                            nonStopList.add(flight)//Non Stop
                        }
                    }
                    secondFinalList.addAll(nonStopList)
                    secondFinalList.addAll(oneStopList)
                    secondFinalList.addAll(twoStopList)
                    secondFinalList.addAll(threeStopList)
                    val isRefundable = flight.fares?.any { it?.refundable == true } ?: false
                    if (isRefundable) {
                        anyFilterUsed = true
                        refundableCount++
                    }
                }
            }

            if (secondFinalList.isNotEmpty()) {
                myFilterList = secondFinalList
            }
            myFilterList.forEach { item ->
                item?.segments?.firstOrNull()?.let { segmentsItem ->
                    segmentsItem.airlineName?.trim()?.uppercase()?.let {
                        val key = it.trim().uppercase()
                        if (availableAirlines.containsKey(key)) {
                            if (availableAirlines.getValue(key).seatBoxDialog.value) {
                                anyFilterUsed = true
                                airlinesFilterList.add(item)
                            }
                        }
                    }
                }
            }
            if (airlinesFilterList.isNotEmpty()) {
                myFilterList = airlinesFilterList
            }
            if (myFilterList.isNotEmpty()) {
                flights.clear()
                flights.addAll(myFilterList)
            } else {
                flights.clear()
                if (anyFilterUsed) {
                    displayDialogFailure("No Flight Found")
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCommissionData(travelType: Int?) {
        api.getCommissionInfo(travelType)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status && it.receivableData != null) {
                    commPack = it.receivableData
                }
            }, {
                it.printStackTrace()
            })
    }
}

data class AirlinesFilters(
    val seatBoxDialog: MutableState<Boolean> = mutableStateOf(false),
    val title: String,
    var count: Int? = 0
)

fun String.toAirLinesFilter(count: Int?): AirlinesFilters {
    return AirlinesFilters(title = this.trim().uppercase(), count = count)
}