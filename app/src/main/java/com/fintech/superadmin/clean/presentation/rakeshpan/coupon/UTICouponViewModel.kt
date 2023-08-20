package com.fintech.superadmin.clean.presentation.rakeshpan.coupon

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.data.dto.CouponDto
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.NetworkUtil
import com.fintech.superadmin.util.ViewUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UTICouponViewModel @Inject constructor(private val api: FintechAPI) :
    BaseViewModel<List<CouponDto>>() {

    var couponType = mutableStateOf("")
    var coupons by mutableStateOf("")

    var resetStatus by mutableStateOf<RegularResponse?>(null)

    init {
        everResetted()
        getUtiS()
    }


    private fun getUtiS() {
        NetworkUtil.getNetworkResult(api.getUTIs(), null) {
            if (it.status) {
                _state.value = ScreenState(receivedResponse = it.receivableData)
            }
        }
    }


    fun selCouponType(context: Context) {
        if (Accessable.isAccessable()) {
            ViewUtils.onSpinnerViewBring(
                "Select Coupon Type",
                context,
                listOf("Physical Coupon", "E-Coupon")
            ) {
                couponType.value = it ?: ""
            }
        }
    }

    fun buyCoupons() {
        if (Accessable.isAccessable()) {
            if (couponType.value.trim().isEmpty()) {
                displayResponseMessage("Select a valid Coupon Type")
            } else if (coupons.trim().isEmpty()) {
                displayResponseMessage("Enter a valid Coupon quantity.")
            } else {
                val type = if (couponType.value.contains("physical", true)) "1" else "2"
                displayDialogLoading("Please wait..")
                NetworkUtil.getNetworkResult(
                    api.purchaseCoupon(
                        coupennum = coupons.trim(),
                        coupentype = type.trim()
                    ), null
                ) {
                    dismissDialog()
                    if (it.data?.txnStatus?.contains("success", true) == true) {
                        displayResponseMessage(it.data.message) {
                            getUtiS()
                        }
                        couponType.value = ""
                        coupons = ""
                    } else {
                        displayResponseMessage(it.data?.message ?: it.message ?: "Some Failure")
                    }
                }
            }
        }
    }

    fun checkCouponStatus(id: String) {
        if (Accessable.isAccessable()) {
            displayDialogLoading("Please wait")
            NetworkUtil.getNetworkResult(api.checkCouponStatus(id = id), null) {
                dismissDialog()
                if (it.data?.txnStatus?.contains("success", true) == true) {
                    displayResponseMessage(it.data.message) {
                        getUtiS()
                    }
                    couponType.value = ""
                    coupons = ""
                } else {
                    displayResponseMessage(it.data?.message ?: it.message ?: "Some Failure")
                }
            }
        }
    }

    private fun everResetted() {
        NetworkUtil.getNetworkResult(api.everResetted(), null) {
            resetStatus = it
        }
    }


    fun doResetPassword() {
        if (Accessable.isAccessable()) {
            displayLoading("Please wait..")
            NetworkUtil.getNetworkResult(api.resetUTIPass(), null) {
                dismissDialog()
                if (it.data?.txnStatus?.contains("success", true) == true) {
                    displayResponseMessage(it.data.message) {
                        everResetted()
                    }
                } else {
                    displayResponseMessage(it.data?.message ?: it.message ?: "Some Failure")
                }
            }
        }
    }

    fun checkResetPassword() {
        if (Accessable.isAccessable()) {
            displayLoading("Please wait..")
            NetworkUtil.getNetworkResult(api.checkResetPassword(resetStatus?.message ?: ""), null) {
                dismissDialog()
                if (it.data?.txnStatus?.contains("success", true) == true) {
                    displayResponseMessage(it.data.txnStatus) {
                        everResetted()
                    }
                } else {
                    displayResponseMessage(it.data?.txnStatus ?: it.message ?: "Some Failure")
                }
            }
        }
    }


}