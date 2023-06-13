package com.fintech.superadmin.clean.presentation.payout

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.cashfree.PayoutBeneficiary
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.presentation.commonComponent.BaseButton
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedTextView
import com.fintech.superadmin.clean.presentation.commonComponent.DropDownSystem
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PayoutViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<List<PayoutBeneficiary>>() {

    var selectedBeneficiaryDialog by mutableStateOf<PayoutBeneficiary?>(null)

    var amount by mutableStateOf("")
    var transactionType by mutableStateOf<String?>(null)
    var otpTimes = 0
    var transactionOTP by mutableStateOf("")
    var transactionOTPHash by mutableStateOf("")


    var transactionTypeList = listOf("IMPS", "NEFT")
    var transactionDialog by mutableStateOf(false)


    @SuppressLint("CheckResult")
    fun getPayoutBeneficiaries() {
        _state.value = ScreenState(isLoading = true)
        api.getPayoutBeneficiaries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = ScreenState(isLoading = false)
                if (it.response_code == 1) {
                    _state.value = ScreenState(receivedResponse = it.receivableData ?: emptyList())
                } else {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
            }, { error ->
                _state.value = ScreenState(error = error.localizedMessage ?: "Some Error")
            })
    }


    private fun sendPayoutOTP(context: Context) {
        if (Accessable.isAccessable()) {
            if (amount.trim().isEmpty()) {
                displayFailureMessage("Enter a valid amount of minimum 100 INR")
            }
            else if (transactionType == null) {
                displayFailureMessage("Select a valid transaction type")
            }else {
                displayDialogLoading("Please Wait..")
                NetworkUtil.getNetworkResult(
                    api.sendPayoutOTP(
                        trans_mode = transactionType?:"",
                        amount = amount,
                    ),
                    context
                ) {
                    dismissDialog()
                    if (it.response_code == 1) {
                        otpTimes++
                        transactionOTPHash = it.receivableData?.OTPHASH ?: ""
                    } else {
                        displayFailureMessage(it.message ?: "Some Error")
                    }
                }
            }
        }
    }

    @Composable
    fun TransactionOTPPage(
        context: Context = LocalContext.current
    ) {
        LaunchedEffect(key1 = true) {
            transactionOTP = ""
            transactionOTPHash = ""
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(15.sdp))
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.sdp, horizontal = 10.sdp)
                    ) {
                        BasicOutlinedTextView(
                            hint = "Full Name",
                            value = selectedBeneficiaryDialog?.NAME ?: "",
                            onValueChange = { },
                            maxLength = 50,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.Characters
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            isEditable = false
                        )
                        Spacer(modifier = Modifier.height(10.sdp))


                        BasicOutlinedTextView(
                            hint = "Account Number",
                            value = selectedBeneficiaryDialog?.ACCOUNT ?: "",
                            onValueChange = { },
                            maxLength = 50,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.None
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            isEditable = false
                        )
                        Spacer(modifier = Modifier.height(10.sdp))


                        BasicOutlinedTextView(
                            hint = "IFSC Code",
                            value = selectedBeneficiaryDialog?.IFSC ?: "",
                            onValueChange = { },
                            maxLength = 10,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.None
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            isEditable = false
                        )
                        Spacer(modifier = Modifier.height(10.sdp))

                        BasicOutlinedTextView(
                            hint = "Amount",
                            value = amount,
                            onValueChange = { amount = it },
                            maxLength = 50,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number,
                                capitalization = KeyboardCapitalization.None
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            isEditable = transactionOTPHash.isEmpty()
                        )
                        Spacer(modifier = Modifier.height(10.sdp))

                        Spacer(modifier = Modifier.height(10.sdp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(40.sdp)
                                    .fillMaxWidth(.8f)
                                    .border(width = 0.5.dp, color = Color.Black),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    fontSize = MaterialTheme.typography.body2.fontSize,
                                    text = if (transactionType == null) "Transaction Type" else transactionType
                                        ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.sdp),
                                    color = if (transactionType == null) Color.Gray else Color.Black,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .height(40.sdp)
                                    .width(40.sdp)
                                    .clickable { transactionDialog = true }
                                    .border(width = 0.5.dp, color = Color.Black),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .width(8.sdp)
                                        .height(14.sdp),
                                    painter = painterResource(id = R.drawable.down_arrow),
                                    contentDescription = "down_arrow"
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(8.sdp))
                        if (transactionDialog) {
                            DropDownSystem(
                                list = transactionTypeList,
                                expanded = transactionDialog,
                                onSelect = {
                                    transactionDialog = false
                                    it?.let {
                                        transactionType = it
                                    }
                                }
                            )
                        }

                        if (transactionOTPHash.isNotEmpty()) {
                            BasicOutlinedTextView(
                                hint = "OTP",
                                value = transactionOTP,
                                onValueChange = { transactionOTP = it },
                                maxLength = 50,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.Characters
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.sdp))
                        }
                    }
                }

            }

            if (transactionOTPHash.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.sdp, horizontal = 20.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    BaseButton(
                        text = "Verify",
                        fontSize = 14.textSdp,
                        onClick = {
                            sendAmount(context)
                        }
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.sdp, horizontal = 20.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BaseButton(
                        text = "Send OTP",
                        fontSize = 14.textSdp,
                        onClick = {
                            sendPayoutOTP(context)
                        }
                    )
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun sendAmount(context: Context) {
        if (Accessable.isAccessable()) {
            if (amount.trim().isEmpty()) {
                displayDialogFailure("Enter a valid amount")
                return
            }
            if (transactionOTP.trim().isEmpty()) {
                displayDialogFailure("Enter a valid OTP")
                return
            }
            else if (transactionType == null) {
                displayFailureMessage("Select a valid transaction type")
            }
            displayDialogLoading("Please Wait..")
            api.doDirectPayout(
                send_amount = amount.trim(),
                bene_id = selectedBeneficiaryDialog?.BENEID ?: "",
                trans_mode = transactionType?:"",
                verify = transactionOTPHash,
                otp = transactionOTP.trim()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dismissDialog()
                    if (it.response_code == 1) {
                        selectedBeneficiaryDialog = null
                        displayDialogSuccess(it.message ?: "Success")
                    } else {
                        displayFailureMessage(it.message ?: "Failed")
                    }
                }, { error ->
                    displayFailureMessage(error.localizedMessage ?: "Some Error")
                })
        }
    }


}