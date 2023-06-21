package com.fintech.superadmin.flight.presentation.seatSelection

import android.annotation.SuppressLint
import com.fintech.superadmin.flight.data.remote.FlightApi
import com.fintech.superadmin.flight.data.remote.dto.airSearch.FlightsItem
import com.fintech.superadmin.flight.data.remote.dto.seatMap.SeatSegmentsItem
import com.fintech.superadmin.flight.domain.ScreenState
import com.fintech.superadmin.flight.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SeatSelectionViewModel @Inject constructor(
    private val api: FlightApi
) : BaseViewModel<List<SeatSegmentsItem?>>() {

    var flight: FlightsItem? = null
    var searchKey: String? = null
    var passengerJson: String? = null

    @SuppressLint("CheckResult")
    fun selectSeatsCall() {
        _state.value = ScreenState(isLoading = true)
        api.getSeats(
            fareid = flight?.fares?.firstOrNull()?.fareId ?: "",
            fkey = flight?.flightKey ?: "",
            skey = searchKey ?: "",
            seatData = passengerJson?:""
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dismissDialog()
                if (it.responseHeader?.errorCode == "0000" && it.responseHeader.errorDesc == "SUCCESS") {
                    it.airSeatMaps?.firstOrNull()?.seatSegments?.let {
                        _state.value = ScreenState(receivedResponse = it)
                    }
                } else {
                    _state.value = ScreenState(error = it.responseHeader?.errorDesc ?: "Some Error")
                }
            }, {
                _state.value = ScreenState(error = it.localizedMessage ?: "Some Error")
            })
    }


}