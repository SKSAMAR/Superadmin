package com.fintech.prepe.auth

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.fintech.prepe.activities.HomeActivity
import com.fintech.prepe.auth.component.AuthNav
import com.fintech.prepe.auth.data.AuthApi
import com.fintech.prepe.clean.common.BaseViewModel
import com.fintech.prepe.constructor.Construct
import com.fintech.prepe.data.network.responses.RegularResponse
import com.fintech.prepe.deer_listener.Receiver
import com.fintech.prepe.util.DisplayMessageUtil
import com.fintech.prepe.util.NetworkUtil
import com.fintech.prepe.util.UtilHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class  AuthViewModel
@Inject constructor(
    private val authApi: AuthApi
) : BaseViewModel<RegularResponse>() {

    lateinit var receiver: Receiver<Boolean>
    val mobile = mutableStateOf("")
    val email = mutableStateOf("")
    val otp = mutableStateOf("")
    var resendAble = mutableStateOf(false)
    var timerCount = mutableStateOf(System.currentTimeMillis())
    val otpHash = mutableStateOf("")
    val password = mutableStateOf("")
    val cPassword = mutableStateOf("")
    val firstName = mutableStateOf("")


    fun loginMerchant(context: Context) {
        displayDialogLoading("Please Wait..")
        authApi.getUserLogin(
            mobile.value.trim(),
            password.value.trim(),
            UtilHolder.getLongitude(),
            UtilHolder.getLatitude()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({authData->
                dismissDialog()
                if (authData.status && authData.rs_code == "200" && authData.login_Auth == 1) {
                    Construct.installToken(context, authData) {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        DisplayMessageUtil.dismissDialog()
                        context.startActivity(intent)
                    }
                } else {
                    displayFailureMessage("${authData.user_Exist} ${authData.status}")
                }
            },{
                displayFailureMessage(it.localizedMessage?:"Some Error")
            })


    }

    fun sendRegisterOTP(navController: NavController, isFirst: Boolean = true) {
        otpHash.value = ""
        if (firstName.value.trim().isEmpty()) {
            displayFailureMessage("Enter a valid first name.")
        } else if (email.value.trim().isEmpty()) {
            displayFailureMessage("Enter a valid email.")
        } else if (mobile.value.trim().isEmpty() || mobile.value.trim().length < 10) {
            displayFailureMessage("Enter a valid mobile number.")
        } else {

            NetworkUtil.getNetworkResultCompose(
                authApi.sendBoardOTP(
                    email = email.value.trim(),
                    mobile = mobile.value.trim()
                ), this
            ) {
                if (it.status) {
                    otpHash.value = it.receivableData ?: ""
                    if (isFirst) {
                        navController.navigate(AuthNav.OTP.route)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "task",
                            value = "create"
                        )
                    }
                } else {
                    displayFailureMessage("" + it.message)
                }
            }
        }

    }

    fun doOnBoard(navController: NavController) {
        if (firstName.value.trim().isEmpty()) {
            displayFailureMessage("Enter a valid first name.")
        } else if (email.value.trim().isEmpty()) {
            displayFailureMessage("Enter a valid email.")
        } else if (mobile.value.trim().isEmpty() || mobile.value.trim().length < 10) {
            displayFailureMessage("Enter a valid mobile number.")
        } else if (password.value.trim().isEmpty() || password.value.trim().length < 5) {
            displayFailureMessage("Enter a valid Password.")
        } else if (cPassword.value.trim().isEmpty() || password.value != cPassword.value) {
            displayFailureMessage("Passwords doesn't match.")
        } else {
            NetworkUtil.getNetworkResultCompose(
                authApi.doOnBoard(
                    email = email.value.trim(),
                    password = password.value.trim(),
                    mobile = mobile.value.trim(),
                    otp = otp.value.trim(),
                    hash = otpHash.value,
                    fname = firstName.value.trim(),
                    gstNumber = "".trim(),
                    referral_code = "".trim()
                ), this
            ) {
                if (it.status) {
                    displaySuccessMessage("" + it.message)
                    loginMerchant(context = navController.context)
                } else if (it.response_code == 202) {
                    navController.popBackStack(AuthNav.OTP.route, false)
                    displayFailureMessage("" + it.message)
                } else {
                    displayFailureMessage("" + it.message)
                }
            }
        }

    }


    fun sendForgotOTP(navController: NavController, isFirst: Boolean = true) {
        otpHash.value = ""
        if (mobile.value.trim().length < 3) {
            displayFailureMessage("Enter a valid email or mobile number")
        } else {

            NetworkUtil.getNetworkResultCompose(
                authApi.forgotPassword(
                    credential = mobile.value.trim(),
                ), this
            ) {
                if (it.status) {
                    otpHash.value = it.receivableData ?: ""
                    if (isFirst) {
                        navController.navigate(AuthNav.OTP.route)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "task",
                            value = "forgot"
                        )
                    }
                } else {
                    displayFailureMessage("" + it.message)
                }
            }
        }
    }

    fun changePassword(navController: NavController) {
        val credential =
            if (mobile.value.trim().length == 10) mobile.value.trim() else email.value.trim()
        if (mobile.value.trim().isEmpty() || mobile.value.trim().length < 10) {
            displayFailureMessage("Enter a valid mobile number.")
        } else if (password.value.trim().isEmpty() || password.value.trim().length < 5) {
            displayFailureMessage("Enter a valid Password.")
        } else if (cPassword.value.trim().isEmpty() || password.value != cPassword.value) {
            displayFailureMessage("Passwords doesn't match.")
        } else {
            NetworkUtil.getNetworkResultCompose(
                authApi.changePassword(
                    password = password.value.trim(),
                    credential = credential.trim(),
                    otp = otp.value.trim(),
                    hash = otpHash.value,
                ), this
            ) {
                if (it.status) {
                    displaySuccessMessage("" + it.message)
                    loginMerchant(context = navController.context)

                } else if (it.response_code == 202) {
                    navController.popBackStack(AuthNav.OTP.route, false)
                    displayFailureMessage("" + it.message)
                } else {
                    displayFailureMessage("" + it.message)
                }
            }
        }

    }

}