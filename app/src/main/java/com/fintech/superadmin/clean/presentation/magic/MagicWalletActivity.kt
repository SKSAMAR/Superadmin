package com.fintech.superadmin.clean.presentation.magic

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MagicWalletActivity : BaseComponentAct() {


    val viewModel by viewModels<MagicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    MagicWalletBalanceContent()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MagicWalletBalanceContent() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Magic Wallet")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = "help",
                            )
                        }
                    }
                )
            }
        ) {

            BasicScreenState(state = viewModel.state) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.sdp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(18.sdp))
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 7.sdp, horizontal = 5.sdp),
                            shape = RoundedCornerShape(10.sdp),
                            elevation = 5.sdp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.sdp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = viewModel.amount,
                                    onValueChange = {
                                        if (it.trim().isDigitsOnly()) {
                                            viewModel.amount = it
                                        }
                                    },
                                    maxLines = 1,
                                    placeholder = {
                                        Text(text = "Enter amount to Withdraw")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.sdp))
                                OutlinedButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        viewModel.withdraw()
                                    }
                                ) {
                                    Text(text = "Withdraw")
                                }
                                Spacer(modifier = Modifier.height(10.sdp))
                            }
                        }
                    }

                }
            }
        }
    }
}