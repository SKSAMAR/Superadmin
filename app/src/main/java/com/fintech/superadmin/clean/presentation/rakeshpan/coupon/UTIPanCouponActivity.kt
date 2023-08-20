package com.fintech.superadmin.clean.presentation.rakeshpan.coupon

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.common.BasicSelectView
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.data.dto.CouponDto
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UTIPanCouponActivity : BaseComponentAct() {

    private val viewModel by viewModels<UTICouponViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    UTICoupons()
                }
            }
        }
    }


    @Composable
    private fun UTICoupons() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Request Pan Card Coupon")
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
            }
        ) {
            BasicScreenState(state = viewModel.state) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.sdp))
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.sdp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.sdp)
                            ) {
                                Text(
                                    text = "You approved for PAN UTI. Purchase coupon",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.sdp))
                                OutlinedButton(
                                    onClick = {
                                        if (viewModel.resetStatus !=null && viewModel.resetStatus?.response_code != 1){
                                            viewModel.doResetPassword()
                                        }else{
                                            viewModel.checkResetPassword()
                                        }
                                    }
                                ) {
                                    Text(text = if (viewModel.resetStatus !=null && viewModel.resetStatus?.response_code != 1) "Reset Password" else "Check Reset Password Status")
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(12.sdp))
                    }


                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.sdp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.sdp)
                            ) {
                                Spacer(modifier = Modifier.height(4.sdp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.selCouponType(this@UTIPanCouponActivity)
                                        },
                                    value = viewModel.couponType.value,
                                    onValueChange = {

                                    },
                                    singleLine = true,
                                    enabled = false,
                                    placeholder = {
                                        Text(text = "Select Coupon Type")
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = ""
                                        )
                                    }
                                )

                                Spacer(modifier = Modifier.height(4.sdp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.sdp),
                                    value = viewModel.coupons,
                                    onValueChange = {
                                        if (it.isEmpty() || it.isDigitsOnly()) {
                                            viewModel.coupons = it
                                        }
                                    },
                                    placeholder = {
                                        Text(text = "Number of Coupons")
                                    },
                                    maxLines = 1,
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.sdp))

                                OutlinedButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.sdp),
                                    onClick = {
                                        viewModel.buyCoupons()
                                    }
                                ) {
                                    Text(text = "Submit")
                                }

                                Spacer(modifier = Modifier.height(4.sdp))
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.sdp))
                    }


                    viewModel.state.value.receivedResponse?.let {
                        items(it) {
                            Coupon(it)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.sdp))
                    }
                }
            }
        }
    }


    @Composable
    private fun Coupon(data: CouponDto) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.sdp, horizontal = 12.sdp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.sdp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(
                        text = "Type: ${if (data.tYPE == "1") "Physical" else "E-Coupon"}",
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "Status: ${data.sTATUS}", modifier = Modifier.weight(1f))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Text(text = "Quantity: ${data.nUM}", modifier = Modifier.fillMaxWidth())
                    Text(text = "RefId: ${data.oDID}", modifier = Modifier.fillMaxWidth())
                }
                if (data.sTATUS?.contains("pending", true) == true) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.sdp),
                        onClick = {
                            viewModel.checkCouponStatus(id = data.oDID ?: "")
                        }
                    ) {
                        Text(text = "Check Status")
                    }
                }
            }
        }
    }

}