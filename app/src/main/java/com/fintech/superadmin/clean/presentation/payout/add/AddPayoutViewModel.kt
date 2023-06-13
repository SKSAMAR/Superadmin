package com.fintech.superadmin.clean.presentation.payout.add

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.presentation.payout.PayoutActivity
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AddPayoutViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<Any>() {

    var beneName by mutableStateOf("")
    var beneEmail by mutableStateOf("")
    var beneMobile by mutableStateOf("")
    var beneAcc by mutableStateOf("")
    var beneIFSC by mutableStateOf("")
    var address by mutableStateOf("")


    @SuppressLint("CheckResult")
    fun addPayoutBeneficiaries(context: Context) {
        if (Accessable.isAccessable()) {
            if (beneName.trim().isEmpty()) {
                displayDialogFailure("Enter a valid Beneficiary Name")
                return
            }
            if (beneEmail.trim().isEmpty()) {
                displayDialogFailure("Enter a valid Beneficiary Email")
                return
            }
            if (beneMobile.trim().isEmpty()) {
                displayDialogFailure("Enter a valid Beneficiary Mobile")
                return
            }
            if (beneAcc.trim().isEmpty()) {
                displayDialogFailure("Enter a valid Beneficiary Account")
                return
            }
            if (beneIFSC.trim().isEmpty()) {
                displayDialogFailure("Enter a valid Beneficiary IFSC")
                return
            }
            if (address.trim().isEmpty()) {
                displayDialogFailure("Enter a valid Beneficiary Address")
                return
            }
            _state.value = ScreenState(isLoading = true)
            api.addPayoutBeneficiaries(
                beneName.trim(),
                beneEmail.trim(),
                beneMobile.trim(),
                beneAcc.trim(),
                beneIFSC.trim(),
                address.trim()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _state.value = ScreenState(isLoading = false)
                    if (it.subCode == "200") {
                        context.showToast(it.message ?: "Success")
                        context.startActivity(Intent(context, PayoutActivity::class.java))
                    } else {
                        _state.value = ScreenState(error = it.message ?: "Some Error")
                    }
                }, { error ->
                    _state.value = ScreenState(error = error.localizedMessage ?: "Some Error")
                })
        }
    }


}