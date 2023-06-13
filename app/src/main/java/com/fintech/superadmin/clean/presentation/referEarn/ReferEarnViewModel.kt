package com.fintech.superadmin.clean.presentation.referEarn

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.refer.ReferDto
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.domain.model.contact.Contact
import com.fintech.superadmin.data.network.APIServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class ReferEarnViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<ReferDto>() {

    var link by mutableStateOf("")


    init {
        earnedAndData()
    }
    @SuppressLint("CheckResult")
    fun earnedAndData(
        initialFetch: Boolean = true
    ){
        _state.value = ScreenState(isLoading = true)
        api.earnedAndData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = ScreenState(receivedResponse = it)
                link = it.REFER_URL?:""
            },{
                _state.value = ScreenState(error = it.localizedMessage?:"Some Error")
            })
    }


}