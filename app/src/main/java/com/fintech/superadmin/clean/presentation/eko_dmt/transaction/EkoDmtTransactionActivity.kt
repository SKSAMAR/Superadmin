package com.fintech.superadmin.clean.presentation.eko_dmt.transaction

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.R
import com.fintech.superadmin.auth.component.DisplayComposeDialog
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedTextView
import com.fintech.superadmin.clean.presentation.commonComponent.DropDownSystem
import com.fintech.superadmin.clean.presentation.dmt.component.BeneficiaryInfo
import com.fintech.superadmin.clean.presentation.eko_dmt.component.EkoBeneficiaryInfo
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.eko.RecipientListItem
import com.fintech.superadmin.data.network.responses.BeneficiaryBank
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EkoDmtTransactionActivity : BaseComponentAct() {

    val viewModel by viewModels<EkoDmtTransactionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializers()
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    TransactionScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun TransactionScreen() {
        val context = LocalContext.current
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Send Money")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { onBackPressedDispatcher.onBackPressed() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "arrow_back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        onClick = {
                            viewModel.sendAmountOTP(context)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.sdp, start = 15.sdp, end = 15.sdp)
                            .height(42.sdp),
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                text = "CONFIRM",
                                color = MaterialTheme.colors.surface,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.textSdp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        ) {
            BasicScreenState(state = viewModel.state) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        viewModel.selectedBeneficiaryItem?.let { beneficiary ->
                            EkoBeneficiaryInfo(
                                senderMobile = viewModel.senderMobile ?: "",
                                beneficiaryItem = beneficiary,
                                isExpandable = false
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 5.sdp, horizontal = 10.sdp)
                        ) {
                            viewModel.selectedBeneficiaryItem?.let { beneficiary ->
                                BasicOutlinedTextView(
                                    hint = "Enter Amount",
                                    value = viewModel.amount,
                                    onValueChange = { viewModel.amount = it },
                                    maxLength = 10,
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number,
                                        capitalization = KeyboardCapitalization.None
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
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
                                            text = if (viewModel.transactionType == null) "Transaction Type" else viewModel.transactionType
                                                ?: "",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.sdp),
                                            color = if (viewModel.transactionType == null) Color.Gray else Color.Black,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .height(40.sdp)
                                            .width(40.sdp)
                                            .clickable { viewModel.transactionDialog = true }
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
                                if (viewModel.transactionDialog) {
                                    DropDownSystem(
                                        list = viewModel.transactionTypeList,
                                        expanded = viewModel.transactionDialog,
                                        onSelect = {
                                            viewModel.transactionDialog = false
                                            it?.let {
                                                viewModel.transactionType = it
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                if (viewModel.transactionOTPDialog){
                    DisplayComposeDialog(
                        onCancel = {
                            viewModel.transactionOTPDialog = false
                        },
                        content = {
                            viewModel.TransactionOTPPage()
                        }
                    )
                }
            }
        }
    }

    private fun initializers() {
        viewModel.senderMobile = intent.getStringExtra("number")
        viewModel.selectedBeneficiaryItem = intent.getParcelableExtra("selectedBankModel") as RecipientListItem?
    }
}