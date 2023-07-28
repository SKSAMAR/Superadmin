package com.fintech.superadmin.clean.presentation.eko_dmt.transaction

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.presentation.commonComponent.BaseButton
import com.fintech.superadmin.clean.presentation.commonComponent.CommonTextField
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.eko.RecipientListItem
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.DisplayMessageUtil
import com.fintech.superadmin.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class EkoDmtTransactionViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<Objects>() {

    var transactionTypeList = listOf("IMPS", "NEFT")
    var selectedBeneficiaryItem: RecipientListItem? = null
    var senderMobile: String? = null
    var amount by mutableStateOf("")
    var transactionDialog by mutableStateOf(false)
    var transactionType by mutableStateOf<String?>(null)
    var otpTimes = 0
    var transactionOTPDialog by mutableStateOf(false)
    var transactionOTP by mutableStateOf("")
    //var transactionOTPHash by mutableStateOf("")


    private fun sendAmount(context: Context) {
        if (Accessable.isAccessable()) {
            if (amount.trim().toInt() < 100) {
                displayFailureMessage("Enter a valid amount of minimum 100 INR")
            } else if (transactionType == null) {
                displayFailureMessage("Select a valid Transaction Type")
            }
            else if (transactionOTP.trim().isEmpty()) {
                displayFailureMessage("Enter a valid M-Pin")
            }
            else {
                displayDialogLoading("Please Wait..")
                NetworkUtil.getNetworkResult(
                    api.ekosendAmountDMT(
                        txn_type = transactionType?.trim() ?: "",
                        senderMobile = senderMobile?.trim() ?: "",
                        bene_id = selectedBeneficiaryItem?.recipientId?.toString() ?: "",
                        send_amount = amount.trim(),
                        send_am_acc = selectedBeneficiaryItem?.account ?: "",
                        ifsc = selectedBeneficiaryItem?.ifsc ?: "",
                        tpin = transactionOTP.trim()
                    ),
                    context
                ) {
                    dismissDialog()
                    if (it.response_code == 1) {
                        amount = ""
                        otpTimes = 0
                        transactionOTPDialog = false
                        DisplayMessageUtil.dmtShow(context, it.dmtTransactions)

                    } else {
                        displayFailureMessage("" + it.message)
                    }
                }
            }
        }
    }



    fun sendAmountOTP(context: Context) {
        if (Accessable.isAccessable()) {
            if (amount.trim().isEmpty()) {
                displayFailureMessage("Enter a valid amount of minimum 100 INR")
                return
            }
            else if (amount.trim().toInt() < 100) {
                displayFailureMessage("Enter a valid amount of minimum 100 INR")
            } else if (transactionType == null) {
                displayFailureMessage("Select a valid Transaction Type")
            } else {
                transactionOTPDialog = true
                return
                /**
                displayDialogLoading("Please Wait..")
                NetworkUtil.getNetworkResult(
                    api.ekosendAmountOTP(
                        otpSendTime = otpTimes.toString(),
                        send_am = amount,
                    ),
                    context
                ) {
                    dismissDialog()
                    if (it.response_code == 1) {
                        otpTimes++
                        transactionOTPDialog = true
                        transactionOTPHash = it.OTPHASH
                    } else {
                        displayFailureMessage("" + it.smsotpst)
                    }
                }
                **/
            }
        }
    }

    @Composable
    fun TransactionOTPPage(
        context: Context = LocalContext.current
    ) {
        LaunchedEffect(key1 = true) {
            transactionOTP = ""
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
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
                        text = "M-PIN Verification",
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.textSdp
                    )
                    Spacer(modifier = Modifier.height(2.sdp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Please enter your M-PIN",
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
                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = transactionOTP,
                        placeholderText = "M-PIN",
                        onValueChange = {
                            transactionOTP = it
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

                BaseButton(
                    text = "Verify",
                    fontSize = 14.textSdp,
                    onClick = {
                        sendAmount(context)
                    }
                )
            }
        }
    }

}