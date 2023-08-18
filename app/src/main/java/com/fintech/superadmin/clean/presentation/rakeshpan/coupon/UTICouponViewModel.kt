package com.fintech.superadmin.clean.presentation.rakeshpan.coupon

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.ViewUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UTICouponViewModel @Inject constructor(private val fintechAPI: FintechAPI) : BaseViewModel<Any>() {

    var couponType = mutableStateOf("")
    var coupons by mutableStateOf("")


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

    }

}