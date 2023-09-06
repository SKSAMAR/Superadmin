package com.fintech.superadmin.activities.transactionDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.domain.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class LegalViewModel @Inject constructor(private val fintechAPI: FintechAPI) :
    BaseViewModel<List<Any?>>() {

    var search by mutableStateOf("")


    fun getLegalServices(info: String) {
        _state.value = ScreenState(isLoading = true)
        fintechAPI.legalDetails(info = info, customerno = search.trim())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.status) {
                        _state.value = ScreenState(receivedResponse = it.receivableData)
                    } else {
                        _state.value = ScreenState(error = it.message ?: "Some Error")
                    }
                }, { error ->
                    _state.value = ScreenState(error = error.localizedMessage ?: "Some Error")
                }
            )
    }


}