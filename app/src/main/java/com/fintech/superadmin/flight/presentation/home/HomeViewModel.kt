package com.fintech.superadmin.flight.presentation.home


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import com.fintech.superadmin.flight.data.remote.FlightApi
import com.fintech.superadmin.flight.data.remote.dto.AirPortDto
import com.fintech.superadmin.flight.data.remote.dto.toFilteredAirlineItem
import com.fintech.superadmin.flight.data.remote.sendDto.AirSearchSendDto
import com.fintech.superadmin.flight.data.remote.sendDto.FilteredAirlineItem
import com.fintech.superadmin.flight.data.remote.sendDto.TripInfoItem
import com.fintech.superadmin.flight.domain.DateModel
import com.fintech.superadmin.flight.domain.state.SharedState.adult
import com.fintech.superadmin.flight.domain.state.SharedState.child
import com.fintech.superadmin.flight.domain.state.SharedState.infant
import com.fintech.superadmin.flight.domain.state.SharedState.multiCityTripList
import com.fintech.superadmin.flight.domain.state.SharedState.selectedSeatClass
import com.fintech.superadmin.flight.domain.state.SharedState.selectedTripValue
import com.fintech.superadmin.flight.domain.state.selectedAirLines
import com.fintech.superadmin.flight.presentation.airSearch.AirSearchActivity
import com.fintech.superadmin.flight.presentation.airlines.AirlinesDialogContent
import com.fintech.superadmin.flight.presentation.airlines.AirlinesViewModel
import com.fintech.superadmin.flight.presentation.airports.AirportsViewModel
import com.fintech.superadmin.flight.presentation.airports.component.AirportDialogContent
import com.fintech.superadmin.flight.presentation.common.BaseViewModel
import com.fintech.superadmin.flight.presentation.common.ContentComposableDialog
import com.fintech.superadmin.flight.presentation.home.component.LocationDestinationModel
import com.fintech.superadmin.flight.presentation.home.component.toTripInfoItem
import com.fintech.superadmin.flight.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val api: FlightApi, private val sharedPreferences: SharedPreferences
) : BaseViewModel<Objects>() {


    /**val tripTypeList = listOf(
    TripModel(id = 0, "ONE WAY"),
    TripModel(id = 1, "ROUND TRIP"),
    TripModel(id = 2, "MULTICITY")
    )**/
    //var selectedTripValue by mutableStateOf(tripTypeList.first())
    //var selectedSeatClass by mutableStateOf(seatClassesList.first())
    var notStopOnly by mutableStateOf(false)

    //var multiCityTripList = mutableStateListOf<LocationDestinationModel>()
    //var selectedAirLines = mutableStateMapOf<String, AirlineDto>()
    //var adult by mutableStateOf(1)
    //var child by mutableStateOf(0)
    //var infant by mutableStateOf(0)


    init {
        if (multiCityTripList.isEmpty()) {
            addMore()
            addMore()
            getSavedFromAirPort()
            getSavedToAirPort()
            todayDepartDates()
        }
    }


    fun addMore() {
        try {
            multiCityTripList.add(
                LocationDestinationModel()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun remove() {
        try {
            multiCityTripList.removeLastOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun todayDepartDates() {

        try {

            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val currentDate = sdf.format(Date())

            val sdf2 = SimpleDateFormat("MMM d")
            val englishDate = sdf2.format(Date())

            val sdf3 = SimpleDateFormat("EEEE")
            val day = sdf3.format(Date())

            val sdf4 = SimpleDateFormat("HH:mm:ss z")
            val time = sdf4.format(Date())
            multiCityTripList.firstOrNull()?.departDateModel?.value = DateModel(
                classicDate = currentDate,
                englishData = englishDate,
                time = time,
                day = day,
                date = Date(),
                millisecond =System.currentTimeMillis()
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**

    fun toSpecificDepartDates(date: Date, givenDateModel: (DateModel) -> Unit = {}) {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val currentDate = sdf.format(date)

    val sdf2 = SimpleDateFormat("MMM, d")
    val englishDate = sdf2.format(date)

    val sdf3 = SimpleDateFormat("EEEE")
    val day = sdf3.format(date)

    val sdf4 = SimpleDateFormat("HH:mm:ss z")
    val time = sdf4.format(date)
    departDateModel = DateModel(
    classicDate = currentDate,
    englishData = englishDate,
    time = time,
    day = day,
    date = date,
    millisecond = date.time
    )

    returnDateModel?.let {
    if (departDateModel.millisecond > it.millisecond) {
    returnDateModel = null
    selectedTripValue = TripModel(id = 0, "ONE WAY")
    }
    }
    }

    fun toSpecificReturnDates(date: Date) {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val currentDate = sdf.format(date)

    val sdf2 = SimpleDateFormat("MMM, d")
    val englishDate = sdf2.format(date)

    val sdf3 = SimpleDateFormat("EEEE")
    val day = sdf3.format(date)

    val sdf4 = SimpleDateFormat("HH:mm:ss z")
    val time = sdf4.format(date)

    returnDateModel = DateModel(
    classicDate = currentDate,
    englishData = englishDate,
    time = time,
    day = day,
    date = date,
    millisecond = date.time
    )
    selectedTripValue = TripModel(id = 2, "ROUND TRIP")
    }
     **/

    fun searchFlight(context: Context) {
        if (Accessable.isAccessable()) {
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
            context.startActivity(Intent(context, AirSearchActivity::class.java))

            //val flightSearch = Gson().toJson(airSearch)

        }
    }

    fun displayAirlinesList(airlinesViewModel: AirlinesViewModel) {
        _state.value.content = {
            ContentComposableDialog(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {
                AirlinesDialogContent(viewModel = airlinesViewModel) {
                    _state.value.baseDialogVisible.value = false
                }
            })
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displayAirportsList(
        airportsViewModel: AirportsViewModel, onSelectedAirport: (AirPortDto) -> Unit
    ) {
        _state.value.content = {
            ContentComposableDialog(onDismissRequest = {
                _state.value.baseDialogVisible.value = false
            }, onAccept = {
                _state.value.baseDialogVisible.value = false
            }, onCancel = {
                _state.value.baseDialogVisible.value = false
            }, content = {
                AirportDialogContent(viewModel = airportsViewModel, onSelectedAirport = {
                    onSelectedAirport(it)
                    _state.value.baseDialogVisible.value = false
                    airportsViewModel.search = ""

                })
            })
        }
        _state.value.baseDialogVisible.value = true
    }

    fun setSavedFromAirPort() {
        try {
            val json = Gson().toJson(multiCityTripList.firstOrNull()?.fromAirPortDto?.value)
            json?.let {
                sharedPreferences.edit().putString("FromAirPort", it).apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSavedToAirPort() {
        try {
            val json = Gson().toJson(multiCityTripList.firstOrNull()?.toAirPortDto?.value)
            json?.let {
                sharedPreferences.edit().putString("ToAirPort", it).apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSavedFromAirPort() {
        val from = sharedPreferences.getString("FromAirPort", "")
        try {
            val airPortDto = Gson().fromJson(from, AirPortDto::class.java)
            airPortDto?.let {
                multiCityTripList.firstOrNull()?.fromAirPortDto?.value = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSavedToAirPort() {
        val from = sharedPreferences.getString("ToAirPort", "")
        try {
            val airPortDto = Gson().fromJson(from, AirPortDto::class.java)
            airPortDto?.let {
                multiCityTripList.firstOrNull()?.toAirPortDto?.value = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun addChild() {
        if (child + adult < 9) {
            child++
        }
    }

    fun deleteChild() {
        if (child > 0) {
            child--
        }
    }

    fun deleteAdult() {
        if (adult > 1) {
            adult--
        }
        if (infant > adult) {
            infant = adult
        }
    }

    fun deleteInfant() {
        if (infant > 0) {
            infant--
        }
    }


    fun addInfant() {
        if (infant < adult) {
            infant++
        } else {
            if (addAdult()) {
                infant++
            }
        }

    }

    fun addAdult(): Boolean {
        if (child + adult < 9) {
            adult++
            return true
        }
        return false
    }


}