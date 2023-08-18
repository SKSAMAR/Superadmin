package com.fintech.superadmin.clean.presentation.rakeshpan

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.rakeshpan.UTIApplyViewModel
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.ui.theme.MyColors
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RakeshPanUTIApply : BaseComponentAct() {

    private val viewModel by viewModels<UTIApplyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    DoOnBoard()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DoOnBoard() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Registration")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.register {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        },
                    containerColor = MyColors.primary,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Register",
                            modifier = Modifier.padding(vertical = 1.sdp),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        ) {
            BasicScreenState(state = viewModel.state) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(15.sdp))
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.sdp)
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.name,
                                onValueChange = {
                                    viewModel.name = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Name")
                                },
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Characters,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.address,
                                onValueChange = {
                                    viewModel.address = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Address")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.pincode,
                                onValueChange = {
                                    viewModel.pincode = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Pin Code")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.homeState,
                                onValueChange = {
                                    viewModel.homeState = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "State")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.phone,
                                onValueChange = {
                                    viewModel.phone = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Phone")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.email,
                                onValueChange = {
                                    viewModel.email = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Email")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.pan,
                                onValueChange = {
                                    viewModel.pan = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Pan Number")
                                },
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Characters,
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.shop,
                                onValueChange = {
                                    viewModel.shop = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Shop")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                )
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.sdp),
                                value = viewModel.adhaar,
                                onValueChange = {
                                    viewModel.adhaar = it
                                },
                                maxLines = 1,
                                placeholder = {
                                    Text(text = "Aadhaar")
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number
                                )
                            )

                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(15.sdp))
                    }

                }
            }
        }
    }

}