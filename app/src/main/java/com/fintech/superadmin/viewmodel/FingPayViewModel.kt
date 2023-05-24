package com.fintech.superadmin.viewmodel

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.fintech.superadmin.R
import com.fintech.superadmin.activities.HomeActivity
import com.fintech.superadmin.activities.fingBoard.FingNav
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.data.apiResponse.fingpay.aadharOTP.SendOTPResponse
import com.fintech.superadmin.data.network.APIServices
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
import com.fintech.superadmin.util.UtilHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class FingPayViewModel
@Inject constructor(private val apiServices: APIServices) : BaseViewModel<Objects>() {

    lateinit var navController: NavHostController
    var panNumber by mutableStateOf("")
    var aadhaarNumber by mutableStateOf("")
    var otp by mutableStateOf("")
    var otpResponse: SendOTPResponse? = null
    var fingerPrint by mutableStateOf("")

    fun verifyPanNumber(context: Context) {
        if (Accessable.isAccessable()) {
            if (panNumber.trim().isEmpty()) {
                displayAnimation(anim = R.raw.uh_oh, "Enter a valid Pan Number")
            } else {
                NetworkUtil.getNetworkResult(
                    apiServices.getPanSubmitResponse(
                        panNumber.trim(),
                        UtilHolder.getLongitude(),
                        UtilHolder.getLatitude()
                    ), context
                ) {
                    if (it.status && it.statusCode == 0) {
                        navController.navigate(FingNav.AadhaarSelection.route)
                    } else {
                        DisplayMessageUtil.error(context, it.message)
                    }
                }
            }
        }
    }


    fun sendOTP(context: Context) {
        if (Accessable.isAccessable()) {
            if (panNumber.trim().isEmpty()) {
                displayAnimation(anim = R.raw.uh_oh, "Enter a valid Pan Number")
            } else {
                NetworkUtil.getNetworkResult(
                    apiServices.sendFingOTP(
                        aadhaarNumber,
                        UtilHolder.getLongitude(),
                        UtilHolder.getLatitude(),
                        "ekycotp"
                    ), context
                ) {
                    if (it.status) {
                        otpResponse = it.data
                        navController.navigate(FingNav.OtpSelection.route)
                    } else {
                        DisplayMessageUtil.error(context, it.message)
                    }
                }

            }
        }
    }

    fun reSendOTP(context: Context) {
        if (Accessable.isAccessable()) {
            if (panNumber.trim().isEmpty()) {
                displayAnimation(anim = R.raw.uh_oh, "Enter a valid Aadhaar Number")
            } else {
                NetworkUtil.getNetworkResult(
                    apiServices.resendFingOTP(
                        aadhaarNumber,
                        otpResponse?.encodeFPTxnId ?: "", otpResponse?.primaryKeyId.toString(),
                        UtilHolder.getLongitude(),
                        UtilHolder.getLatitude(),
                        "reekycotp"
                    ), context
                ) {
                    if (it.status) {
                        otpResponse = it.data
                    } else {
                        DisplayMessageUtil.error(context, it.message)
                    }
                }

            }
        }
    }

    fun validateOTP(context: Context) {
        if (Accessable.isAccessable()) {
            if (otp.trim().isEmpty()) {
                displayAnimation(anim = R.raw.uh_oh, "Enter a valid OTP")
            } else {
                NetworkUtil.getNetworkResult(
                    apiServices.validateFingOTP(
                        aadhaarNumber, otp.trim(),
                        otpResponse?.encodeFPTxnId ?: "", otpResponse?.primaryKeyId.toString(),
                        UtilHolder.getLongitude(),
                        UtilHolder.getLatitude(),
                        "validateOTP"
                    ), context
                ) {
                    it.data?.let { send ->
                        otpResponse = send
                    }
                    if (it.status) {
                        navController.navigate(FingNav.CompleteKyc.route)
                    } else {
                        DisplayMessageUtil.error(context, it.message)
                    }
                }
            }
        }
    }

    fun finalizeCompleteKyc(context: Context, fingerData: String) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context)
            NetworkUtil.getNetworkResult(
                apiServices.completeFingKyc(
                    aadhaarNumber.trim(),
                    fingerData,
                    otp,
                    otpResponse?.encodeFPTxnId ?: "",
                    otpResponse?.primaryKeyId.toString(),
                    UtilHolder.getLongitude(),
                    UtilHolder.getLatitude()
                ), context
            ) {
                DisplayMessageUtil.dismissDialog()
                if (it.status){
                    val intent = Intent(context, HomeActivity::class.java)
                    intent.putExtra("status", true)
                    intent.putExtra("message", it.message)
                    context.startActivity(intent)
                }
                else{
                    DisplayMessageUtil.error(context, it.toString())
                }
            }
        }
    }

}