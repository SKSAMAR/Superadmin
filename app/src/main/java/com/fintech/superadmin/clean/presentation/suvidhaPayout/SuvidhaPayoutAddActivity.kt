package com.fintech.superadmin.clean.presentation.suvidhaPayout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.commonComponent.BasicOutlinedTextView
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuvidhaPayoutAddActivity : BaseComponentAct() {

    val viewModel by viewModels<SuvidhaPayoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    AddPayoutScreen()
                }
            }
        }
    }

    @Composable
    private fun AddPayoutScreen() {
        BaseScaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Account")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "arrow_back"
                        )
                    }
                },

                )
        }, bottomBar = {
            BottomAppBar(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.addPayoutBeneficiaries(context = this@SuvidhaPayoutAddActivity)
                }) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add Account",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.surface
                    )
                }
            }
        }) {
            BasicScreenState(state = viewModel.state) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 5.sdp, horizontal = 10.sdp)
                        ) {
                            BasicOutlinedTextView(
                                hint = "Full Name",
                                value = viewModel.name,
                                onValueChange = { viewModel.name = it },
                                maxLength = 50,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.Characters
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.sdp))


                            BasicOutlinedTextView(
                                hint = "Account Number",
                                value = viewModel.accountNumber,
                                onValueChange = { viewModel.accountNumber = it },
                                maxLength = 50,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.None
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.sdp))


                            BasicOutlinedTextView(
                                hint = "IFSC Code",
                                value = viewModel.ifscCode,
                                onValueChange = { viewModel.ifscCode = it },
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
        }
    }

}