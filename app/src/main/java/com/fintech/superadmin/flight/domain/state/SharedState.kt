package com.fintech.superadmin.flight.domain.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.flight.data.remote.dto.AirlineDto
import com.fintech.superadmin.flight.domain.model.TripModel
import com.fintech.superadmin.flight.presentation.home.component.LocationDestinationModel

var selectedAirLines = mutableStateMapOf<String, AirlineDto>()

object SharedState {
    val tripTypeList = listOf(
        TripModel(id = 0, "ONE WAY"),
        TripModel(id = 1, "ROUND TRIP"),
        TripModel(id = 2, "MULTICITY")
    )

    //var selectedAirLines = mutableStateMapOf<String, AirlineDto>()
    var selectedTripValue by mutableStateOf(tripTypeList.first())
    var selectedSeatClass by mutableStateOf(seatClassesList.first())
    var multiCityTripList = mutableStateListOf<LocationDestinationModel>()
    var adult by mutableStateOf(1)
    var child by mutableStateOf(0)
    var infant by mutableStateOf(0)





}