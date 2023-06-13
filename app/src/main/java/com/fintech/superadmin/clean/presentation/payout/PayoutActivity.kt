package com.fintech.superadmin.clean.presentation.payout

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fintech.superadmin.auth.component.DisplayComposeDialog
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.payout.add.PayoutAddActivity
import com.fintech.superadmin.clean.presentation.payout.component.BeneficiaryData
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayoutActivity : BaseComponentAct() {

    val viewModel by viewModels<PayoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    PayoutScreen()
                }
            }
        }
    }

    @Composable
    private fun PayoutScreen() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "X-Payout")
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
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.surface,
                    onClick = {
                        startActivity(Intent(this@PayoutActivity, PayoutAddActivity::class.java))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ) {
            BasicScreenState(state = viewModel.state) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    viewModel.state.value.receivedResponse?.let { list ->
                        items(list) {
                            BeneficiaryData(
                                it
                            ) {
                                viewModel.selectedBeneficiaryDialog = it
                            }
                        }
                    }
                }
                if (viewModel.selectedBeneficiaryDialog != null) {
                    DisplayComposeDialog(
                        onCancel = {
                            viewModel.selectedBeneficiaryDialog = null
                        },
                        content = {
                            viewModel.TransactionOTPPage()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPayoutBeneficiaries()
    }
}