package com.fintech.superadmin.clean.presentation.suvidhaPayout

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fintech.superadmin.auth.component.DisplayComposeDialog
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedSearchView
import com.fintech.superadmin.clean.presentation.payout.add.PayoutAddActivity
import com.fintech.superadmin.clean.presentation.payout.component.BeneficiaryData
import com.fintech.superadmin.clean.presentation.suvidhaPayout.component.SuvidhaBeneficiaryData
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuvidhaPayout : BaseComponentAct() {

    private val viewModel by viewModels<SuvidhaPayoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    SuvidhaPayoutScreen()
                }
            }
        }
    }


    @Composable
    private fun SuvidhaPayoutScreen() {
        LaunchedEffect(key1 = viewModel.search) {
            viewModel.getBeneficiaries()
        }

        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Bank Transfer")
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
                        startActivity(
                            Intent(
                                this@SuvidhaPayout,
                                SuvidhaPayoutAddActivity::class.java
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.sdp))
                BasicOutlinedSearchView(
                    modifier = Modifier.fillMaxWidth(.9f),
                    value = viewModel.search,
                    hint = "Search Account",
                    onValueChange = {
                        viewModel.search = it
                    }
                )
                Spacer(modifier = Modifier.height(5.sdp))
                BasicScreenState(state = viewModel.state) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        viewModel.state.value.receivedResponse?.let { list ->
                            items(list) {
                                SuvidhaBeneficiaryData(
                                    data = it
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBeneficiaries()
    }

}