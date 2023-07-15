package com.fintech.superadmin.auth

import PinView
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.activities.HomeActivity
import com.fintech.superadmin.auth.component.AuthNav
import com.fintech.superadmin.auth.data.AuthApi
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.presentation.commonComponent.BaseButton
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.constructor.Construct
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.deer_listener.Receiver
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
import com.fintech.superadmin.util.UtilHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
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
    private val mobileOtp = mutableStateOf("")
    private val mobileOtpHash = mutableStateOf("")
    var referral_code = mutableStateOf("")
    var resendAble = mutableStateOf(false)
    var timerCount = mutableStateOf(System.currentTimeMillis())
    val otpHash = mutableStateOf("")
    val password = mutableStateOf("")
    val cPassword = mutableStateOf("")
    val firstName = mutableStateOf("")

    var mobileVerificationDialog by mutableStateOf(false)



    @SuppressLint("CheckResult")
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
                if (authData.rs_code == "200") {
                    Construct.installToken(context, authData) {
                        authData.otp = ""
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        DisplayMessageUtil.dismissDialog()
                        context.startActivity(intent)
                    }
                }
                if (authData.rs_code == "201") {
                    mobileOtpHash.value = authData.otp?:""
                    mobileVerificationDialog = true
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
                    referral_code = referral_code.value.trim()
                ), this
            ) {
                if (it.status) {
                    navController.popBackStack(AuthNav.LoginPage.route, false)
                    displaySuccessMessage("" + it.message)
                    //loginMerchant(context = navController.context)
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


    @Composable
    fun MobileOTPPage(
        context: Context = LocalContext.current
    ) {
        LaunchedEffect(key1 = mobileOtpHash.value) {
            if (mobileOtpHash.value.trim().isNotEmpty()) {
                resendAble.value = false
                timerCount.value = System.currentTimeMillis() + 300000
                delay(300000)
                resendAble.value = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(15.sdp))
                    AsyncImage(
                        model = R.drawable.otp_page_vector,
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            //.padding(vertical = 30.sdp)
                            .align(Alignment.CenterHorizontally)
                            .size(130.sdp)
                    )
                }

                Spacer(modifier = Modifier.height(15.sdp))
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "OTP Verification",
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.textSdp
                    )
                    Spacer(modifier = Modifier.height(2.sdp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Please enter your 6 Digit One Time \n" +
                                "Password ( OTP ). This OTP is valid for\n" +
                                "5 minutes",
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 12.textSdp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(25.sdp))

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.sdp), contentAlignment = Alignment.Center
                ) {
                    PinView(
                        pinText = mobileOtp.value,
                        onPinTextChange = {
                            if (it.length < 7) {
                                mobileOtp.value = it
                            }

                        }
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.sdp, horizontal = 20.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            if (Accessable.isAccessable()) {
                                loginMerchant(context)
                            }
                        },
                    text = buildAnnotatedString {
                        append("Didn't received OTP? ")
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("Resend")
                        }
                    },
                    fontSize = 12.textSdp,
                )

                Spacer(modifier = Modifier.height(30.sdp))
                BaseButton(
                    text = "Verify",
                    fontSize = 14.textSdp,
                    onClick = {
                        if (Accessable.isAccessable()) {
                            verifyMobileOTP(context)
                        }
                    }
                )
            }
        }
    }

    private fun verifyMobileOTP(context: Context) {
        if (mobile.value.trim().isEmpty() || mobile.value.trim().length < 10) {
            displayFailureMessage("Enter a valid mobile number.")
        } else if (mobileOtp.value.trim().isEmpty() || mobileOtp.value.trim().length < 6) {
            displayFailureMessage("Enter a valid OTP.")
        } else {
            displayDialogLoading("Please wait..")
            NetworkUtil.getNetworkResult(
                authApi.verifyLogin(
                    otp_mobile = mobile.value.trim(),
                    otp_password = password.value.trim(),
                    enteredOtp  = mobileOtp.value.trim(),
                    otp_store = mobileOtpHash.value,
                    longitude = UtilHolder.getLongitude(),
                    latitude = UtilHolder.getLatitude()
                ), context
            ) {
                dismissDialog()
                if (it.rs_code == "200") {
                    it.otp = ""
                    mobileVerificationDialog = false
                    dismissDialog()
                    Construct.installToken(context, it) {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        DisplayMessageUtil.dismissDialog()
                        context.startActivity(intent)
                    }
                } else {
                    displayFailureMessage("Failed ${it.user_Exist} ${it.status}")
                }
            }
        }

    }

}