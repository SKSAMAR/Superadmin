package com.fintech.superadmin.flight.presentation.airlines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.flight.data.remote.FlightApi
import com.fintech.superadmin.flight.data.remote.dto.AirlineDto
import com.fintech.superadmin.flight.domain.ScreenState
import com.fintech.superadmin.flight.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AirlinesViewModel
@Inject constructor(private val api: FlightApi) : BaseViewModel<List<AirlineDto>>() {
    var search by mutableStateOf("")

    fun getAirlines(){
        _state.value = ScreenState(isLoading = true)
        api.getAirlines(search = search.trim())
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status && it.response_code == 1){
                    it.receivableData?.let {
                        _state.value = ScreenState(receivedResponse = it)
                    }
                }else{
                    _state.value = ScreenState(error = it.message?:"Some Error")
                }
            },{
                _state.value = ScreenState(error = it.localizedMessage?:"Some Error")
            })
    }

}