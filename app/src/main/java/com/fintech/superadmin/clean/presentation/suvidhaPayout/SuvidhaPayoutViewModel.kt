package com.fintech.superadmin.clean.presentation.suvidhaPayout

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.fintech.superadmin.activities.profile.ProfileDetails
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.suvidhaPayout.SuvidhaBeneficiary
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.presentation.commonComponent.BaseButton
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedTextView
import com.fintech.superadmin.clean.presentation.commonComponent.DropDownSystem
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SuvidhaPayoutViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<List<SuvidhaBeneficiary>>() {

    var selectedBeneficiaryDialog by mutableStateOf<SuvidhaBeneficiary?>(null)
    var accountNumber by mutableStateOf("")
    var ifscCode by mutableStateOf("")
    var name by mutableStateOf("")
    var amount by mutableStateOf("")

    var search by mutableStateOf("")


    @SuppressLint("CheckResult")
    fun addPayoutBeneficiaries(context: Context) {
        if (Accessable.isAccessable()) {
            if (name.trim().isEmpty()) {
                displayResponseMessage("Enter a valid name")
            } else if (accountNumber.trim().isEmpty()) {
                displayResponseMessage("Enter a valid account number")
            } else if (ifscCode.trim().isEmpty()) {
                displayResponseMessage("Enter a valid IFSC Code")
            } else {
                displayDialogLoading("Please wait")
                api.addBeneficiary(
                    beneName = name.trim(),
                    accNum = accountNumber.trim(),
                    ifsc = ifscCode.trim()
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dismissDialog()
                        if (it.status) {
                            displayDialogSuccess(it.message ?: "Success")
                        } else if (it.response_code == 5 || it.response_code == 6 || it.response_code == 7) {
                            displayResponseMessage(it.message ?: "Some Error") {
                                context.startActivity(Intent(context, ProfileDetails::class.java))
                            }
                        } else {
                            displayResponseMessage(it.message ?: "Some Error")
                        }
                    }, {
                        displayResponseMessage(it.localizedMessage ?: "Some Error")
                    })
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getBeneficiaries() {
        _state.value = ScreenState(isLoading = true)
        api.getSuvidhaBeneficiary(search = search.trim())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status) {
                    _state.value = ScreenState(receivedResponse = it.receivableData)
                } else {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
            }, {
                _state.value = ScreenState(error = it.localizedMessage ?: "Some Error")
            })
    }


    @Composable
    fun TransactionOTPPage(
        context: Context = LocalContext.current
    ) {
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
                            isEditable = true
                        )
                        Spacer(modifier = Modifier.height(10.sdp))
                    }
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.sdp, horizontal = 20.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BaseButton(
                    text = "Send Amount",
                    fontSize = 14.textSdp,
                    onClick = {
                        sendAmount(context)
                    }
                )
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
            if (selectedBeneficiaryDialog == null) {
                displayDialogFailure("Select a valid Beneficiary")
                return
            }
            displayDialogLoading("Please Wait..")
            api.doSuvidhaaPayout(
                amount = amount.trim(),
                beneRowId = selectedBeneficiaryDialog?.ID ?: "",
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dismissDialog()
                    if (it.response_code == 1) {
                        displayDialogSuccess(it.message ?: "Success")
                        selectedBeneficiaryDialog = null
                        amount = ""
                    } else {
                        displayFailureMessage(it.message ?: "Failed")
                    }
                }, { error ->
                    displayFailureMessage(error.localizedMessage ?: "Some Error")
                })
        }
    }


}