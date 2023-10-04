package com.fintech.superadmin.activities.products

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.common.ScreenAnimation
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.dto.OrderDto
import com.fintech.superadmin.flight.presentation.common.DateDialogSys.showDateDialog
import com.fintech.superadmin.ui.theme.FarLightestGrey
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.SearchBar
import com.fintech.superadmin.util.toSpecificDepartDatesString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersListActivity : BaseComponentAct() {

    val viewModel by viewModels<OrdersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    OrdersListScreen()
                }
            }
        }
    }


    @Composable
    private fun OrdersListScreen() {
        LaunchedEffect(key1 = viewModel.search) {
            viewModel.getOrdersLists()
        }
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "Orders List")
            }, navigationIcon = {
                IconButton(
                    onClick = {
                        onBackPressedDispatcher.onBackPressed()
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
            })
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = FarLightestGrey),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier.padding(vertical = 7.dp),
                    hint = "Transaction Id or Date",
                    value = viewModel.search,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showDateDialog{
                                   viewModel.search = toSpecificDepartDatesString(it)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "calendar"
                            )
                        }
                    },
                    onValueChange = {
                        viewModel.search = it
                    }
                )
                BasicScreenState(state = viewModel.state) {
                    viewModel.state.value.receivedResponse?.let {
                        if (it.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(it) {
                                    OrderDataInfo(
                                        orderDto = it
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @Composable
    private fun OrderDataInfo(
        modifier: Modifier = Modifier,
        orderDto: OrderDto,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.sdp, vertical = 4.sdp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.sdp)
            ) {
                orderDto.pRODUCTINFO?.iMAGE?.let {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.sdp)
                            .padding(vertical = 2.sdp),
                        contentScale = ContentScale.Fit,
                        model = it,
                        loading = {
                            ScreenAnimation(resources = R.raw.loading)
                        },
                        error = {
                            ScreenAnimation(resources = R.raw.not_found)
                        },
                        contentDescription = "image"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.sdp)
                    ) {
                        Text(
                            text = "Name",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = orderDto.fIRSTNAME ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.sdp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Amount",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${stringResource(id = R.string.rupee_sign)}  ${orderDto.aMOUNT ?: "0"}",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.sdp)
                    ) {
                        Text(
                            text = "Discount",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${stringResource(id = R.string.rupee_sign)}  ${orderDto.pRODUCTINFO?.dISCOUNT ?: "0"}",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.sdp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Courier Charge",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${stringResource(id = R.string.rupee_sign)}  ${orderDto.pRODUCTINFO?.cOURIERCHARGE ?: "0"}",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }


                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        value = orderDto.fIRSTNAME ?: "",

                        placeholderText = "Full Name",

                        )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        value = orderDto.aDDRESS ?: "",

                        placeholderText = "Address",

                        )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        value = orderDto.lANDMARK ?: "",

                        placeholderText = "Landmark",

                        )

                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        value = orderDto.cITY ?: "",

                        placeholderText = "City",

                        )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = orderDto.sTATE ?: "",

                        placeholderText = "State",

                        )

                    AddressTextField(
                        modifier = Modifier.weight(1f),
                        value = orderDto.pINCODE ?: "",
                        placeholderText = "Pin Code",

                        )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AddressTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = orderDto.dATE ?: "",
                        placeholderText = "Date",
                    )
                }

            }
        }
    }


    @Composable
    private fun AddressTextField(
        modifier: Modifier = Modifier,
        value: String,
        placeholderText: String = "",
    ) {


        Text(
            modifier = modifier.padding(vertical = 2.sdp, horizontal = 2.sdp),
            text = "$placeholderText: $value",
            fontSize = 12.textSdp
        )
    }
}